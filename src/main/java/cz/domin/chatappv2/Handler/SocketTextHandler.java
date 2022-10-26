package cz.domin.chatappv2.Handler;

import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Helper.Sockets.ChatSocketSessionInfo;
import cz.domin.chatappv2.Service.MessageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {
    Map<String, ChatSocketSessionInfo> sessions = new HashMap<>();

    private MessageService messageService;


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
            } else if (sessions.get(chatUuid).getPersonUuid() == null) {
                sessions.get(chatUuid).setPersonUuid(personUuid);
                sessions.get(chatUuid).setPersonWebSocketSession(session);
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, NullPointerException, IndexOutOfBoundsException {
        final String path = session.getUri().getPath();
        final String uuid = path.substring(path.lastIndexOf("/") + 1);

        final String chatUuid = uuid.substring(0, 36);
        final String personUuid = uuid.substring(36, 72);

        ServiceResponse<Void> serviceResponse = messageService.saveMessage(chatUuid, personUuid, message.getPayload());

        if (sessions.containsKey(chatUuid)) {
            if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
                if (Objects.equals(sessions.get(chatUuid).getMainPersonUuid(), personUuid)) {
                    sessions.get(chatUuid)
                            .getMainPersonWebSocketSession()
                            .sendMessage(
                                    new TextMessage(serviceResponse.getMessage())
                            );
                }
                if (Objects.equals(sessions.get(chatUuid).getPersonUuid(), personUuid)) {
                    sessions.get(chatUuid)
                            .getPersonWebSocketSession()
                            .sendMessage(
                                    new TextMessage(serviceResponse.getMessage())
                            );
                }
            }

            if (sessions.get(chatUuid).getPersonUuid() != null) {
                sessions.get(chatUuid).getPersonWebSocketSession().sendMessage(new TextMessage(message.getPayload()));
            }
            if (sessions.get(chatUuid).getMainPersonUuid() != null) {
                sessions.get(chatUuid).getMainPersonWebSocketSession().sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String path = session.getUri().getPath();
        String uuid = path.substring(path.lastIndexOf("/") + 1);

        String chatUuid = uuid.substring(0, 36);

        sessions.remove(chatUuid);
    }
}
