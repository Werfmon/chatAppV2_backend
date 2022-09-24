package cz.domin.chatappv2.Model.InitializationData;

import cz.domin.chatappv2.Model.FriendshipStatus;
import cz.domin.chatappv2.Repository.FriendshipStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FriendshipStatusDataLoader implements ApplicationRunner {
    private final FriendshipStatusRepository friendshipStatusRepository;

    public void run(ApplicationArguments args) {
        FriendshipStatus friendshipStatus;

        friendshipStatus = new FriendshipStatus(1, "Accepted");
        friendshipStatusRepository.save(friendshipStatus);

        friendshipStatus = new FriendshipStatus(2, "Waiting");
        friendshipStatusRepository.save(friendshipStatus);

        friendshipStatus = new FriendshipStatus(3, "Rejected");
        friendshipStatusRepository.save(friendshipStatus);
    }
}
