package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.create.NewPersonDTO;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertor;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertorResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Model.VisibilityStatus;
import cz.domin.chatappv2.Repository.PersonRepository;
import cz.domin.chatappv2.Repository.VisibilityStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;
    private final VisibilityStatusService visibilityStatusService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Person getPersonByEmail(String email) {
        return personRepository
                .findPersonByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Person with email not found"));
    }
    public Person create(NewPersonDTO newPersonDTO) {
        Person person = modelMapper.map(newPersonDTO, Person.class);

        if (this.isUserExistsWithEmail(person.getEmail())) {
            return null;
        }

        person.setRegistrationDate(LocalDateTime.now());

        Base64ImageConvertorResponse convertorResponse =
                Base64ImageConvertor.save(newPersonDTO.getBase64Image(), person.getUuid());

        if(convertorResponse.isStatus()) {
            person.setImagePath(convertorResponse.getResponse());
        } else {
            log.warn(convertorResponse.getResponse());
        }

        person.setPassword(bCryptPasswordEncoder.encode(newPersonDTO.getPassword()));
        person.setVisibility(visibilityStatusService.getById(VisibilityStatus.VISIBLE));

        return personRepository.save(person);
    }
    public Boolean isUserExistsWithEmail(String email) {
        try {
            Person person = this.getPersonByEmail(email);
            return person != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}