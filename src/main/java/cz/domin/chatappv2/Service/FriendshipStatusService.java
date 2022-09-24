package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Repository.FriendshipStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendshipStatusService {
    private final FriendshipStatusRepository friendshipStatusRepository;

    public FriendshipStatus getById(Integer id) {
        return friendshipStatusRepository.findById(id).orElse(null);
    }
}
