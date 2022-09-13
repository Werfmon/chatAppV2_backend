package cz.domin.chatappv2.repository;


import cz.domin.chatappv2.Model.PersonSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonSettingsRepository extends JpaRepository<PersonSettings, UUID> {
}
