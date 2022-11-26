package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Model.PersonSubscribe;
import cz.domin.chatappv2.Service.PersonService;
import cz.domin.chatappv2.Service.PersonSubscribeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/person/subscribe")
@AllArgsConstructor
@Slf4j
public class PersonSubscribeController {
    private final PersonSubscribeService personSubscribeService;
    private final PersonService personService;

    @PutMapping
    public Response<Void> updateToken(Authentication authentication, @RequestBody Map<String, String> token) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);
        ServiceResponse<Void> serviceResponse = personSubscribeService.updateToken(person, token.get("token"));

        if (serviceResponse.getStatus() == ServiceResponse.OK) {
            return new Response<>(null, HttpStatus.OK, serviceResponse.getMessage(), true);
        }
        return new Response<>(null, HttpStatus.BAD_REQUEST, "", false);
    }
}
