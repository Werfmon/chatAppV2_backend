package cz.domin.chatappv2.repository;


import cz.domin.chatappv2.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
