package cz.domin.chatappv2.Model.InitializationData;

import cz.domin.chatappv2.Model.VisibilityStatus;
import cz.domin.chatappv2.Repository.VisibilityStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VisibilityStatusDataLoader implements ApplicationRunner {
    private final VisibilityStatusRepository visibilityStatusRepository;

    public void run(ApplicationArguments args) {
        VisibilityStatus visibilityStatus;

        visibilityStatus = new VisibilityStatus(1, "Visible");
        visibilityStatusRepository.save(visibilityStatus);

        visibilityStatus = new VisibilityStatus(2, "Visible for friends");
        visibilityStatusRepository.save(visibilityStatus);

        visibilityStatus = new VisibilityStatus(3, "Invisible");
        visibilityStatusRepository.save(visibilityStatus);
    }
}
