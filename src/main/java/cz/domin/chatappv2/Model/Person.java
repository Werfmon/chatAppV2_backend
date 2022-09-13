package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    private UUID uuid = UUID.randomUUID();
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(length = 40)
    private String nickname;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column(name = "registration_date")
    private Date registrationDate;
    @Column(name = "last_login")
    private Date lastLogin;
    @OneToOne
    @JoinColumn(referencedColumnName = "uuid", name = "person_settings_uuid")
    private PersonSettings personSettings;
    @OneToMany(mappedBy = "person1")
    private List<Chat> chats = new ArrayList<>();
}
