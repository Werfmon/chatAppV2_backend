package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.read.ReadMessageDTO;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Message;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Repository.ChatRepository;
import cz.domin.chatappv2.Repository.MessageRepository;
import cz.domin.chatappv2.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private PersonRepository personRepository;
    private ModelMapper modelMapper;


    public ServiceResponse<Void> saveMessage(String chatUuid, String personUUid, String messageText) {
        Message message = new Message();
        Chat chat = chatRepository.findById(chatUuid).orElse(null);
        Person person = personRepository.findById(personUUid).orElse(null);

        if (chat == null) {
            return new ServiceResponse<>(null, "{\"text\": \"Cannot find chat\", \"status\": \"0\" }", ServiceResponse.ERROR);
        }
        if (person == null) {
            return new ServiceResponse<>(null, "{\"text\": \"Cannot find person\", \"status\": \"0\" }", ServiceResponse.ERROR);
        }

        message.setChat(chat);
        message.setPerson(person);
        message.setText(messageText);

        messageRepository.save(message);

        return new ServiceResponse<>(null, "Message saved", ServiceResponse.OK);
    }
    public ServiceResponse<List<ReadMessageDTO>> getChatMessagesBy(String chatUuid, Integer limit, Integer offset) {
        List<Message> messages = messageRepository.findMessagesByChat(chatUuid, limit, offset);

        List<ReadMessageDTO> readMessageDTOs = messages.stream().map(m -> modelMapper.map(m, ReadMessageDTO.class)).toList();

        return new ServiceResponse<>(readMessageDTOs, "Returned Messages", ServiceResponse.OK);
    }
}
