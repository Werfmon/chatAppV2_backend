package cz.domin.chatappv2.Repository;


import cz.domin.chatappv2.Model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {
}