package cz.domin.chatappv2.Helper;

import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@AllArgsConstructor
@NoArgsConstructor
public class ControllerHelpers {
    private PersonService personService;
    public Person getPersonFromAuthentication(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        return personService.getPersonByEmail(email);
    }
}
