package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Controller.dto.read.ReadChatDTO;
import cz.domin.chatappv2.Controller.dto.read.ReadMessageDTO;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.ChatService;
import cz.domin.chatappv2.Service.MessageService;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
@Slf4j
public class ChatController {
    private final PersonService personService;
    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/current-person")
    public Response<List<ReadChatDTO>> getPersonChats(Authentication authentication, @RequestParam String search, @RequestParam Integer limit, @RequestParam Integer offset) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication", false);
        }
        ServiceResponse<List<ReadChatDTO>> serviceResponse = chatService.getPersonChats(person.getUuid(), search, limit, offset);
        if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }
        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }
    @GetMapping("/{chat_uuid}/messages")
    public Response<List<ReadMessageDTO>> getChatMessageBy(@PathVariable(name = "chat_uuid") String chatUuid, @RequestParam Integer limit, @RequestParam Integer offset) {
        ServiceResponse<List<ReadMessageDTO>> serviceResponse = messageService.getChatMessagesBy(chatUuid, limit, offset);

        if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }
        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }
    @GetMapping("/{chat_uuid}/message/last")
    public Response<ReadMessageDTO> getLastChatMessage(@PathVariable(name = "chat_uuid") String chatUuid) {
        ServiceResponse<ReadMessageDTO> serviceResponse = messageService.findLastChatMessage(chatUuid);

        if (serviceResponse.getStatus() == ServiceResponse.OK) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
        }
        return new Response<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{chat_uuid}/messages/set-seen/latest")
    public Response<Void> setSeenLastMessagesAfterPerson(Authentication authentication, @PathVariable(name = "chat_uuid") String chatUuid) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);
        ServiceResponse<Void> serviceResponse = messageService.setAllLastMessagesToSeen(person, chatUuid);
        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }
}
