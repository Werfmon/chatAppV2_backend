package cz.domin.chatappv2.service;

import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;
    public Person getPersonByEmail(String email) {
        return personRepository
                .findPersonByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("PersonRepository>> Person with email not found"));
    }
}