package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "friendship")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Friendship {
    @Id
    @Column(columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "main_person", nullable = false)
    private Person mainPerson;

    @ManyToOne
    @JoinColumn(name = "person", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            referencedColumnName = "id"
    )
    private FriendshipStatus status;
}
