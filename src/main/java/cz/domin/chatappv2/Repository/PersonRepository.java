package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, String> {
    Optional<Person> findPersonByEmail(String email);
}
