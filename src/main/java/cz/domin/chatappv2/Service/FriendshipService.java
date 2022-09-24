package cz.domin.chatappv2.Service;

import com.sun.xml.bind.v2.TODO;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Friendship;
import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Repository.FriendshipRepository;
import cz.domin.chatappv2.Repository.FriendshipStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendshipService {
    private final PersonService personService;
    private final FriendshipStatusService friendshipStatusService;
    private final FriendshipRepository friendshipRepository;

    public ServiceResponse<Friendship> createFriendship(String mainUuid, String uuid) {
        Friendship friendship = new Friendship();

        Person mainPerson = personService.getPersonByUuid(mainUuid);
        Person person = personService.getPersonByUuid(uuid);

        if (mainPerson == null || person == null) {
            return new ServiceResponse<>(null, "Cannot find people", ServiceResponse.ERROR);
        }

        Boolean friendshipExists =
                friendshipRepository.existsFriendshipByMainPersonAndPerson(mainPerson, person);
        Boolean reverseFriendshipExists =
                friendshipRepository.existsFriendshipByMainPersonAndPerson(person, mainPerson);

        if (friendshipExists || reverseFriendshipExists) {
            return new ServiceResponse<>(null, "Friendship with these people exists", ServiceResponse.ERROR);
        }

        friendship.setMainPerson(mainPerson);
        friendship.setPerson(person);
        friendship.setStatus(friendshipStatusService.getById(FriendshipStatus.WAITING));

        Friendship savedFriendship = friendshipRepository.save(friendship);

        return new ServiceResponse<>(savedFriendship, "Friendship created", ServiceResponse.OK);
    }
}
