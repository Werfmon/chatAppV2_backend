package cz.domin.chatappv2.Controller;

import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
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
        ServiceResponse<Person> serviceResponse = personService.create(newPersonDTO);
        Response<Person> response;

        if (serviceResponse.getStatus() == ServiceResponse.OK) {
            response = new Response(serviceResponse.getData(), HttpStatus.OK, serviceResponse.getMessage());
        } else {
            response = new Response(serviceResponse.getData(), HttpStatus.BAD_REQUEST, serviceResponse.getMessage());
        }

        return response;
    }
}
