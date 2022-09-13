package cz.domin.chatappv2.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {
    Map<String, WebSocketSession> sessions = new HashMap<>();
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException, NullPointerException {
//        String path = session.getUri().getPath();
//        String id = path.substring(path.lastIndexOf("/") + 1);
//        userSocketSessions.put(session.getId(), id);
        if(!sessions.containsKey(session.getId())) {
            sessions.put(session.getId(), session);
        }
        sessions.forEach((k, v) -> {
            try {
                v.sendMessage(new TextMessage("Hi " + " how may we help you?"));
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        });
    }

}
