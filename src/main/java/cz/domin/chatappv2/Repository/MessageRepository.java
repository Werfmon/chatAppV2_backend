package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(value = "SELECT * FROM Message m INNER JOIN Chat c WHERE c.uuid = :chatUuid ORDER BY m.sent_date ASC LIMIT :offset, :limit", nativeQuery = true)
    List<Message> findMessagesByChat(@Param("chatUuid") String chatUuid, Integer limit, Integer offset);
}
