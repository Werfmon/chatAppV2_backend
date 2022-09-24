package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Model.VisibilityStatus;
import cz.domin.chatappv2.Repository.VisibilityStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class VisibilityStatusService {
    private final VisibilityStatusRepository visibilityStatusRepository;

    public VisibilityStatus getById(Integer id) {
        return visibilityStatusRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found with id " + id));
    }
}
