package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Controller.dto.read.ReadPersonDTO;
import cz.domin.chatappv2.Model.Message;
import cz.domin.chatappv2.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(value = "SELECT * FROM Message m LEFT JOIN Chat c ON m.chat_uuid = c.uuid WHERE c.uuid = :chatUuid ORDER BY m.sent_date DESC LIMIT :offset, :limit", nativeQuery = true)
    List<Message> findMessagesByChat(String chatUuid, Integer limit, Integer offset);

    @Query(value = "SELECT * FROM Message m  WHERE m.chat_uuid = :chatUuid ORDER BY m.sent_date DESC LIMIT 1", nativeQuery = true)
    Optional<Message> findTopByChat_Uuid(String chatUuid);

    List<Message> findMessagesByChat_UuidAndAndSeenAndPersonIsNot(String chatUuid, Boolean seen, Person person);
}
