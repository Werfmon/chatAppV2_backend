package cz.domin.chatappv2.Handler;

import com.google.gson.Gson;
import cz.domin.chatappv2.Controller.dto.create.FCM.FCMNotificationPart;
import cz.domin.chatappv2.Controller.dto.create.FCM.FCMRequestDTO;
import cz.domin.chatappv2.Helper.Notifications.SendNotification;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Helper.Sockets.ChatSocketSessionInfo;
import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.ChatService;
import cz.domin.chatappv2.Service.MessageService;
import cz.domin.chatappv2.Service.PersonService;
import cz.domin.chatappv2.Service.PersonSubscribeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {
    Map<String, ChatSocketSessionInfo> sessions = new HashMap<>();

    private final MessageService messageService;
    private final PersonService personService;
    private final ChatService chatService;
    private final PersonSubscribeService personSubscribeService;
    private final SendNotification sendNotification;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Trying to connect socket");
        final String path = session.getUri().getPath();
        final String uuid = path.substring(path.lastIndexOf("/") + 1);

        final String personUuid = uuid.substring(36, 72);
        final String chatUuid = uuid.substring(0, 36);

        if (!sessions.containsKey(chatUuid)) {
            sessions.put(chatUuid, new ChatSocketSessionInfo());
        }

        if(sessions.containsKey(chatUuid)) {
            if (sessions.get(chatUuid).getMainPersonUuid() == null) {
                sessions.get(chatUuid).setMainPersonUuid(personUuid);
                sessions.get(chatUuid).setMainPersonWebSocketSession(session);
            } else if (sessions.get(chatUuid).getPersonUuid() == null && !Objects.equals(sessions.get(chatUuid).getMainPersonUuid(), personUuid)) {
                sessions.get(chatUuid).setPersonUuid(personUuid);
                sessions.get(chatUuid).setPersonWebSocketSession(session);
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, NullPointerException, IndexOutOfBoundsException {
        log.info("Socket connection handling messages");

        final String path = session.getUri().getPath();
        final String uuid = path.substring(path.lastIndexOf("/") + 1);

        final String chatUuid = uuid.substring(0, 36);
        final String personUuid = uuid.substring(36, 72);

        ServiceResponse<Void> serviceResponse = messageService.saveMessage(chatUuid, personUuid, message.getPayload());
        log.info("Socket> service message: " + serviceResponse.getMessage());
// TODO: implementovat error hlasky

//        if (sessions.containsKey(chatUuid)) {
//            if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
//                if (Objects.equals(sessions.get(chatUuid).getMainPersonUuid(), personUuid)) {
//                    sessions.get(chatUuid)
//                            .getPersonWebSocketSession()
//                            .sendMessage(
//                                    new TextMessage(serviceResponse.getMessage())
//                            );
//                }
//                if (Objects.equals(sessions.get(chatUuid).getPersonUuid(), personUuid)) {
//                    sessions.get(chatUuid)
//                            .getMainPersonWebSocketSession()
//                            .sendMessage(
//                                    new TextMessage(serviceResponse.getMessage())
//                            );
//                }
//            }

            log.info(sessions.get(chatUuid).getMainPersonUuid());
            log.info(sessions.get(chatUuid).getPersonUuid());

            if (Objects.equals(sessions.get(chatUuid).getPersonUuid(), personUuid)) {
                if (sessions.get(chatUuid).getMainPersonUuid() != null) {
                    sessions.get(chatUuid).getMainPersonWebSocketSession().sendMessage(new TextMessage(message.getPayload()));
                } else {
                    sendNotification.sendNotification(chatUuid, personUuid, message);
                }
            } else {
                if (sessions.get(chatUuid).getPersonUuid() != null) {
                    sessions.get(chatUuid).getPersonWebSocketSession().sendMessage(new TextMessage(message.getPayload()));
                } else {
                    sendNotification.sendNotification(chatUuid, personUuid, message);
                }
            }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        final String path = session.getUri().getPath();

        final String uuid = path.substring(path.lastIndexOf("/") + 1);
        final String chatUuid = uuid.substring(0, 36);
        final String personUuid = uuid.substring(36, 72);

        if (Objects.equals(sessions.get(chatUuid).getPersonUuid(), personUuid)) {
            sessions.get(chatUuid).setPersonUuid(null);
            try {
                sessions.get(chatUuid).getPersonWebSocketSession().close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        if (Objects.equals(sessions.get(chatUuid).getMainPersonUuid(), personUuid)) {
            sessions.get(chatUuid).setMainPersonUuid(null);
            try {
                sessions.get(chatUuid).getMainPersonWebSocketSession().close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        if (sessions.get(chatUuid).getPersonUuid() == null && sessions.get(chatUuid).getMainPersonUuid() == null) {
            sessions.remove(chatUuid);
        }
    }
}
