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
    private UUID uuid = UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name = "person_1")
    private Person person1;
    @ManyToOne
    @JoinColumn(name = "person_2")
    private Person person2;
    @Column(name = "chat_color", length = 7)
    private String chatColor = "#DE7D0B";
}