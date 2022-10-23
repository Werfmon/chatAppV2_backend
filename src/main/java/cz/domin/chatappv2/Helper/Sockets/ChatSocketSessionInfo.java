package cz.domin.chatappv2.Helper.Sockets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatSocketSessionInfo {
    private WebSocketSession personWebSocketSession;
    private WebSocketSession mainPersonWebSocketSession;
    private String personUuid;
    private String mainPersonUuid;
}
