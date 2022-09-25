package cz.domin.chatappv2.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    @Id
    @Column(columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 40, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "image_path", nullable = false)
    private String imagePath = "src/main/resources/static/images/avatars/default.png";

    @ManyToOne
    @JoinColumn(
            nullable = false,
            referencedColumnName = "id",
            name = "visibility_id"
    )
    private VisibilityStatus visibility;
}
