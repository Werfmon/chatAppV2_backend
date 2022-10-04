package cz.domin.chatappv2.Controller.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadPersonDTO {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String nickname;
    private String password;
    private String base64Image = null;
}
