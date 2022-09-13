package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "person_settings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonSettings {
    @Id
    private UUID uuid = UUID.randomUUID();
    @Column(name = "visible_for_others")
    private Boolean visibleForOthers = true;
    @Column(name = "image_url")
    private String imageUrl;

    public PersonSettings(Boolean visibleForOthers) {
        this.visibleForOthers = visibleForOthers;
        this.imageUrl = "/src/main/resources/static/images/avatars/default.png";
    }
}
