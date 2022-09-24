package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @Column(columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @Column(length = 7, nullable = false)
    private String color = "#DE7D0B";

    @ManyToOne
    @JoinColumn(nullable = false)
    private Friendship friendship;

}
