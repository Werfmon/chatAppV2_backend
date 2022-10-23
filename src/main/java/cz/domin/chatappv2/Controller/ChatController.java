package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Controller.dto.read.ReadChatDTO;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.ChatService;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {
    private final PersonService personService;
    private final ChatService chatService;
    @GetMapping("/current-person")
    public Response<List<ReadChatDTO>> getPersonChats(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication", false);
        }
        ServiceResponse<List<ReadChatDTO>> serviceResponse = chatService.getPersonChats(person.getUuid());
        if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }
        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }
}
