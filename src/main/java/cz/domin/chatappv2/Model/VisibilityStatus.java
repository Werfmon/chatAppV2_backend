package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "visibility_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VisibilityStatus {
    public static final Integer VISIBLE = 1;
    public static final Integer VISIBLE_BY_FRIENDS = 2;
    public static final Integer INVISIBLE = 3;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String name;
}
