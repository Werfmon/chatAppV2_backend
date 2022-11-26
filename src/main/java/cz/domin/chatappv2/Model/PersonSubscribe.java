package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "person_subscribe")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonSubscribe {
    @Id
    @Column(columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @Column(nullable = false, length = 1024)
    private String token;
}
