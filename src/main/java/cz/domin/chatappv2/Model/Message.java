package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
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
    @Column
    private String text;
    @Column(name = "sent_date")
    private Date sentDate;
    @ManyToOne
    private Person sender;
    @ManyToOne
    private Chat chat;
}
