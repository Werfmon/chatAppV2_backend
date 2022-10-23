package cz.domin.chatappv2.Handler;

import cz.domin.chatappv2.Helper.Sockets.ChatSocketSessionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {
    Map<String, ChatSocketSessionInfo> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        String uuid = path.substring(path.lastIndexOf("/") + 1);

        String personUuid = uuid.substring(36, 36);
        String chatUuid = uuid.substring(0, 36);

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
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException, NullPointerException {
        String path = session.getUri().getPath();
        String uuid = path.substring(path.lastIndexOf("/") + 1);

        String chatUuid = uuid.substring(0, 36);
        if (sessions.containsKey(chatUuid)) {
            if (sessions.get(chatUuid).getPersonUuid() != null && sessions.get(chatUuid).getMainPersonUuid() != null) {
                sessions.get(chatUuid).getMainPersonWebSocketSession().sendMessage(new TextMessage("Hello chat: " + chatUuid));
                sessions.get(chatUuid).getPersonWebSocketSession().sendMessage(new TextMessage("Hello chat: " + chatUuid));

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
