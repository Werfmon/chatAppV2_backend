package cz.domin.chatappv2.Controller;

import com.google.api.client.util.IOUtils;
import cz.domin.chatappv2.Controller.dto.read.ReadPersonDTO;
import cz.domin.chatappv2.Controller.dto.update.UpdatePasswordDTO;
import cz.domin.chatappv2.Helper.ControllerHelpers;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Controller.dto.create.NewPersonDTO;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Slf4j
public class PersonController  {
    private final PersonService personService;

    @PostMapping("/registration")
    public Response<Person> create(@RequestBody NewPersonDTO newPersonDTO) {
        ServiceResponse<Person> serviceResponse = personService.create(newPersonDTO);
        Response<Person> response;

        if (serviceResponse.getStatus() == ServiceResponse.OK) {
            response = new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
        } else {
            response = new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }

        return response;
    }
    @GetMapping("/all-available")
    public Response<List<ReadPersonDTO>> getAllAvailablePeople(Authentication authentication, @RequestParam(name = "search") String searchText) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        ServiceResponse<List<ReadPersonDTO>> serviceResponse = personService.getAllAvailablePeople(person.getUuid(), searchText);

        return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
    }

    @PutMapping("/avatar")
    public Response<Void> updatePersonAvatar(Authentication authentication, @RequestBody HashMap<String, String> body) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        String base64Image = body.get("base64Image");

        ServiceResponse<Void> serviceResponse = personService.updatePersonAvatar(person, base64Image);

        if (serviceResponse.getStatus()) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
        }
        return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
    }

    @PutMapping("/password")
    public Response<String> changeOldPassword(Authentication authentication, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        String email = authentication.getPrincipal().toString();
        Person person = personService.getPersonByEmail(email);

        ServiceResponse<String> serviceResponse = personService.changePasswordPassword(person, updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
        if (serviceResponse.getStatus()) {
            return new Response<>(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage(), true);
        } else {
            return new Response<>(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage(), false);
        }
    }
}
