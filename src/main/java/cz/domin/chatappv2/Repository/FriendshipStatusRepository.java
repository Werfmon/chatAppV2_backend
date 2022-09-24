package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Integer> {
}
