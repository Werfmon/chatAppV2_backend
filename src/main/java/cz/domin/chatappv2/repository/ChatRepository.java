package cz.domin.chatappv2.repository;

import cz.domin.chatappv2.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
