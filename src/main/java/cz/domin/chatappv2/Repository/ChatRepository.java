package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query(value = "SELECT distinct(c.uuid), c.*, mp.* , p.* " +
            "FROM chat as c " +
            "INNER JOIN friendship as f ON c.friendship_uuid = f.uuid " +
            "INNER JOIN person as p ON f.person = p.uuid " +
            "INNER JOIN person as mp ON f.main_person = mp.uuid " +
            "INNER JOIN message as m ON m.chat_uuid = c.uuid " +
            "WHERE f.status_id = :friendshipStatusId  AND (p.uuid = :uuid OR mp.uuid = :uuid) " +
            "AND (lower(p.nickname) LIKE lower(:searchText) OR lower(p.last_name) LIKE lower(:searchText) OR lower(p.first_name) LIKE lower(:searchText)) " +
            "ORDER BY m.sent_date " +
            "DESC LIMIT :offset, :limit",
            nativeQuery = true)
    List<Chat> findChatsBy(String uuid, Integer friendshipStatusId, String searchText, int limit, int offset);
}
