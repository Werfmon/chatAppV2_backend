package cz.domin.chatappv2.Controller.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewPersonDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String nickname;
    private String password;
    private String base64Image = null;
}
