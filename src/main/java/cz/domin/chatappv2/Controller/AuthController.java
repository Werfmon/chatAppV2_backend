package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Controller.dto.read.ReadPersonDTO;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final PersonService personService;
    @GetMapping("/logged")
    public Response<ReadPersonDTO> getLoggedPerson(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(null, HttpStatus.BAD_REQUEST, "User not found", false);
        }
        ServiceResponse<ReadPersonDTO> serviceResponse = personService.getPersonForResponse(person);

        if (serviceResponse.getStatus()) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), serviceResponse.getStatus());
        } else {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), serviceResponse.getStatus());
        }
    }
    @GetMapping("/valid-jwt")
    public Response<Boolean> testJWT(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(false, HttpStatus.BAD_REQUEST, "JWT is invalid", false);
        }
        return new Response<>(true, HttpStatus.BAD_REQUEST, "JWT is valid", true);
    }
}