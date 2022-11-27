package cz.domin.chatappv2.Controller.dto.read;

import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadMessageDTO {
    private String uuid;
    private LocalDateTime sentDate;
    private String text;
    private String chatUuid;
    private Person person;
    private Boolean seen;
}
