package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findChatsByFriendship_MainPersonOrFriendship_PersonAndFriendship_Status(Person mainPerson, Person person, FriendshipStatus friendshipStatus);
}
