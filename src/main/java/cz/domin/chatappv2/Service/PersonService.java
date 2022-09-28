package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.create.NewPersonDTO;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertor;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertorResponse;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Friendship;
import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Model.VisibilityStatus;
import cz.domin.chatappv2.Repository.FriendshipRepository;
import cz.domin.chatappv2.Repository.FriendshipStatusRepository;
import cz.domin.chatappv2.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;
    private final VisibilityStatusService visibilityStatusService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipStatusService friendshipStatusService;

    public Person getPersonByUuid(String uuid) {
        return personRepository.findById(uuid).orElse(null);
    }
    public Person getPersonByEmail(String email) {
        return personRepository
                .findPersonByEmail(email)
                .orElse(null);
    }
    public ServiceResponse<Person> create(NewPersonDTO newPersonDTO) {
        Person person = modelMapper.map(newPersonDTO, Person.class);

        if (this.isUserExistsWithEmail(person.getEmail())) {
            return new ServiceResponse<>(null, "User exists with this email", ServiceResponse.ERROR);
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

        Person savedPerson = personRepository.save(person);

        return new ServiceResponse<>(savedPerson, "Person was registered", ServiceResponse.OK);
    }
    public Boolean isUserExistsWithEmail(String email) {
        return this.getPersonByEmail(email) != null;
    }

    public ServiceResponse<List<Person>> getAllAvailablePeople(String personUuid, String searchText) {
        Person person = this.getPersonByUuid(personUuid);
        List<Person> people =
                personRepository.findPeopleByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrNicknameContainsIgnoreCaseAndVisibility(
                        searchText,
                        searchText,
                        searchText,
                        visibilityStatusService.getById(VisibilityStatus.VISIBLE)
                );
        // TODO: 9/25/2022 pridat funkcionalitu, aby vracel pouze lidi, ktere jeste nema v friendship, pokud ma tak pouze REJECTED status  

        List<Friendship> friendships =
                friendshipRepository.findFriendshipsByMainPersonOrPersonAndStatusNot(
                        person,
                        person,
                        friendshipStatusService.getById(FriendshipStatus.REJECTED)
                );

        people = people
                .stream()
                .filter(p -> !p.getUuid().equals(personUuid))
                .filter(p ->
                        friendships
                                .stream()
                                .filter(f -> {
                                    if (f.getPerson().getUuid() == p.getUuid() || f.getMainPerson().getUuid() == p.getUuid()) {
                                        return false;
                                    }
                                    return true;
                                })
                                .collect(Collectors.toList())
                                .isEmpty()
                )
                .collect(Collectors.toList());

        return new ServiceResponse<>(people, "Available people", ServiceResponse.OK);
    }
}