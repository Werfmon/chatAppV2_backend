package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Model.PersonSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonSubscribeRepository extends JpaRepository<PersonSubscribe, String> {
    Optional<PersonSubscribe> findPersonSubscribeByPerson(Person person);
}
