package cz.domin.chatappv2.Service;

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

    public Friendship createFriendship(String mainUuid, String uuid) {
        Friendship friendship = new Friendship();

        Person mainPerson = personService.getPersonByUuid(mainUuid);
        Person person = personService.getPersonByUuid(uuid);

        if (mainPerson == null || person == null) {
            return null;
        }

        friendship.setMainPerson(mainPerson);
        friendship.setPerson(person);
        friendship.setStatus(friendshipStatusService.getById(FriendshipStatus.WAITING));

        return friendshipRepository.save(friendship);
    }
}
