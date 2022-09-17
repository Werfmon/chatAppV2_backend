package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private UUID uuid = UUID.randomUUID();

    @Column(name = "sent_date", nullable = false)
    private Date sentDate;

    @Column(nullable = false)
    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Person person;
}
