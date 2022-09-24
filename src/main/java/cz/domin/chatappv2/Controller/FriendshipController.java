package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Friendship;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.FriendshipService;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
@AllArgsConstructor

public class FriendshipController {
    private final FriendshipService friendshipService;
    private final PersonService personService;

    @PostMapping("/{uuid}/add")
    public Response createFriendship(Authentication authentication, @PathVariable String uuid) {
        String email = authentication.getPrincipal().toString();
        Person mainPerson = personService.getPersonByEmail(email);

        if (mainPerson == null) {
            return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication");
        }

        ServiceResponse<Friendship> serviceResponse = friendshipService.createFriendship(mainPerson.getUuid(), uuid);

        Response response;

        if(serviceResponse.getStatus() == ServiceResponse.OK) {
            response = new Response(serviceResponse.getData(), HttpStatus.OK, "Waiting to accept");
        } else {
            response = new Response(null, HttpStatus.BAD_REQUEST, serviceResponse.getMessage());
        }
        return response;
    }
}
