package cz.domin.chatappv2.Controller.dto.read;

import cz.domin.chatappv2.Model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadFriendshipDTO {
    private String uuid;
    private ReadPersonDTO mainPerson;
    private ReadPersonDTO person;
}
