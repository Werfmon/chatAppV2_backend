package cz.domin.chatappv2.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;

@Entity
@Table(name = "friendship_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendshipStatus {
    public static final Integer ACCEPTED = 1;
    public static final Integer WAITING = 2;
    public static final Integer REJECTED = 3;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String name;
}
