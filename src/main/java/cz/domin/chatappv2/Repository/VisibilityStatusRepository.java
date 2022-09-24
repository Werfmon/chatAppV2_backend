package cz.domin.chatappv2.Repository;

import cz.domin.chatappv2.Model.VisibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisibilityStatusRepository extends JpaRepository<VisibilityStatus, Integer> {
}
