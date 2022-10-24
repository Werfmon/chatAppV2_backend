package cz.domin.chatappv2.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "sent_date", nullable = false)
    private LocalDateTime sentDate = LocalDateTime.now();

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
