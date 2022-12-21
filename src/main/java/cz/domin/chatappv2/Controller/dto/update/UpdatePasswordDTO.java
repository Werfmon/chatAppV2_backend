package cz.domin.chatappv2.Controller.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
