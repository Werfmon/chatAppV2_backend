package cz.domin.chatappv2.Controller.dto.create.FCM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FCMDataPart {
    private String personUuid;
    private String sentDate;
}
