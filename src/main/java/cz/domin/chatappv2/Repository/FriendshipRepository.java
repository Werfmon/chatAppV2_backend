package cz.domin.chatappv2.Repository;


import cz.domin.chatappv2.Model.Friendship;
import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    Boolean existsFriendshipByMainPersonAndPersonAndStatusNot(Person mainPerson, Person person, FriendshipStatus friendshipStatus);
    Optional<Friendship> findFriendshipByMainPersonAndPerson(Person mainPerson, Person person);
    List<Friendship> findFriendshipsByMainPersonOrPersonAndStatusNot(Person mainPerson, Person person, FriendshipStatus friendshipStatus);
    List<Friendship> findFriendshipsByPersonAndStatus(Person person, FriendshipStatus friendshipStatus);
}