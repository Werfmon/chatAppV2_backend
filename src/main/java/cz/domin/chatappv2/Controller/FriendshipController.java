package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Controller.dto.read.ReadPersonDTO;
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

import java.util.List;

@RestController
@RequestMapping("/friendship")
@AllArgsConstructor

public class FriendshipController {
    private final FriendshipService friendshipService;
    private final PersonService personService;

    @PostMapping("/{uuid}/add")
    public Response<Friendship> createFriendship(Authentication authentication, @PathVariable String uuid) {
        String email = authentication.getPrincipal().toString();
        Person mainPerson = personService.getPersonByEmail(email);

        if (mainPerson == null) {
            return new Response<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication", false);
        }

        ServiceResponse<Friendship> serviceResponse = friendshipService.createFriendship(mainPerson.getUuid(), uuid);


        if(serviceResponse.getStatus() == ServiceResponse.OK) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, "Waiting to accept", true);
        }
        return new Response<>(null, HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
    }
    @PutMapping("/{main_uuid}/accept")
    public Response<Friendship> acceptFriendship(Authentication authentication, @PathVariable(name = "main_uuid") String mainPersonUuid) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication", false);
        }

        ServiceResponse<Friendship> serviceResponse = friendshipService.acceptFriendship(person.getUuid(), mainPersonUuid);

        if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }

        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }
    @PutMapping("/{main_uuid}/reject")
    public Response<Friendship> rejectFriendship(Authentication authentication, @PathVariable(name = "main_uuid") String mainPersonUuid) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        if (person == null) {
            return new Response<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error with authentication", false);
        }

        ServiceResponse<Friendship> serviceResponse = friendshipService.rejectFriendship(person.getUuid(), mainPersonUuid);

        if (serviceResponse.getStatus() == ServiceResponse.ERROR) {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }

        return new Response<>(serviceResponse.getData(), HttpStatus.OK, "Friendship was rejected", true);
    }
    @GetMapping("/all/waiting")
    public Response<List<ReadPersonDTO>> getWaitingFriendships(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        ServiceResponse<List<ReadPersonDTO>> serviceResponse = friendshipService.getWaitingFriendships(person.getUuid());

        if (serviceResponse.getStatus() == ServiceResponse.OK) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
        }
        return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
    }

}
