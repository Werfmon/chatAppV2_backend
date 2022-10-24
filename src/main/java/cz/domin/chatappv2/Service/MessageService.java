package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Message;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Repository.ChatRepository;
import cz.domin.chatappv2.Repository.MessageRepository;
import cz.domin.chatappv2.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private PersonRepository personRepository;


    public ServiceResponse<Void> saveMessage(String chatUuid, String personUUid, String messageText) {
        Message message = new Message();
        Chat chat = chatRepository.findById(chatUuid).orElse(null);
        Person person = personRepository.findById(personUUid).orElse(null);
        log.info(personUUid);
        log.info(chatUuid);
        if (chat == null) {
            return new ServiceResponse<>(null, "Chat not found", ServiceResponse.ERROR);
        }
        if (person == null) {
            return new ServiceResponse<>(null, "Person not found", ServiceResponse.ERROR);
        }
        message.setChat(chat);
        message.setPerson(person);
        message.setText(messageText);

        messageRepository.save(message);

        return new ServiceResponse<>(null, "Message saved", ServiceResponse.OK);
    }
}
