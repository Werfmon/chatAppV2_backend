package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Controller.dto.create.NewPersonDTO;
import cz.domin.chatappv2.Service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService personService;


    @PostMapping("/registration")
    public Response create(@RequestBody NewPersonDTO newPersonDTO) {
        Person person = personService.create(newPersonDTO);
        Response<Person> response;

        if (person != null) {
            response = new Response(
                    person,
                    HttpStatus.OK,
                    "User was registered"
            );
        } else {
            response = new Response(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "User with this email exists, try different"
            );
        }

        return response;
    }
}
