package cz.domin.chatappv2.Controller.dto.create.FCM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FCMRequestDTO {
    private String to;
    private FCMNotificationPart notification;
}