package cz.domin.chatappv2.Repository;


import cz.domin.chatappv2.Model.Friendship;
import cz.domin.chatappv2.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    Boolean existsFriendshipByMainPersonAndPerson(Person mainPerson, Person person);
    Optional<Friendship> findFriendshipByMainPersonAndPerson(Person mainPerson, Person person);
}