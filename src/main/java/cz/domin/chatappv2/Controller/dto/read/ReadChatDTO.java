package cz.domin.chatappv2.Controller.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadChatDTO {
    private String uuid;
    private String color;
    private ReadFriendshipDTO friendship;
}
