package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Model.PersonSubscribe;
import cz.domin.chatappv2.Repository.PersonSubscribeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonSubscribeService {
    private final PersonSubscribeRepository personSubscribeRepository;

    public ServiceResponse<Void> updateToken(Person person, String token) {
        PersonSubscribe personSubscribe = personSubscribeRepository.findPersonSubscribeByPerson(person).orElse(null);
        if (personSubscribe == null) {
            personSubscribe = new PersonSubscribe();
            personSubscribe.setPerson(person);
            personSubscribe.setToken(token);
        } else {
            personSubscribe.setToken(token);
        }
        personSubscribeRepository.save(personSubscribe);

        return new ServiceResponse<>(null, "Updated token in PersonSubscribe", ServiceResponse.OK);
    }
    public ServiceResponse<String> getTokenByPerson(Person person) {
        PersonSubscribe personSubscribe = personSubscribeRepository.findPersonSubscribeByPerson(person).orElse(null);
        if (personSubscribe != null) {
            return new ServiceResponse<>(personSubscribe.getToken(), "FCM Token from PersonSubscribe", ServiceResponse.OK);
        }
        return new ServiceResponse<>(null, "Error, not found PersonSubscribe", ServiceResponse.ERROR);
    }
}
