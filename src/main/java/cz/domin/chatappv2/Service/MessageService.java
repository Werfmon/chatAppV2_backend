package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.read.ReadChatDTO;
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

import java.util.ArrayList;
import java.util.Collections;
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


    public ServiceResponse<Void> saveMessage(String chatUuid, String personUUid, String messageText, Boolean seen) {
        Message message = new Message();
        Chat chat = chatRepository.findById(chatUuid).orElse(null);
        Person person = personRepository.findById(personUUid).orElse(null);

        if (chat == null) {
            return new ServiceResponse<>(null, "{\"message\": \"Cannot find chat\", \"status\": \"0\" }", ServiceResponse.ERROR);
        }
        if (person == null) {
            return new ServiceResponse<>(null, "{\"message\": \"Cannot find person\", \"status\": \"0\" }", ServiceResponse.ERROR);
        }

        message.setChat(chat);
        message.setPerson(person);
        message.setText(messageText);
        message.setSeen(seen);

        messageRepository.save(message);

        return new ServiceResponse<>(null, "Message saved", ServiceResponse.OK);
    }
    public ServiceResponse<List<ReadMessageDTO>> getChatMessagesBy(String chatUuid, Integer limit, Integer offset) {
        List<Message> messages = messageRepository.findMessagesByChat(chatUuid, limit, offset);

        List<ReadMessageDTO> readMessageDTOs = new ArrayList<>(messages.stream().map(m -> modelMapper.map(m, ReadMessageDTO.class)).toList());
        Collections.reverse(readMessageDTOs);
        return new ServiceResponse<>(readMessageDTOs, "Returned Messages", ServiceResponse.OK);
    }
    public ServiceResponse<ReadMessageDTO> findLastChatMessage(String chatUuid) {
        Message message = messageRepository.findTopByChat_Uuid(chatUuid).orElse(null);

        if (message == null) {
            ReadMessageDTO readChatDTO = new ReadMessageDTO();
            readChatDTO.setChatUuid(chatUuid);
            readChatDTO.setPerson(null);
            readChatDTO.setSentDate(null);
            readChatDTO.setUuid(null);
            readChatDTO.setSeen(true);
            readChatDTO.setText("Start your conversation");
            return new ServiceResponse<>(readChatDTO, "0 messages", ServiceResponse.OK);
        }

        ReadMessageDTO readChatDTO = modelMapper.map(message, ReadMessageDTO.class);

        return new ServiceResponse<>(readChatDTO, "Returned last message", ServiceResponse.OK);
    }
    public ServiceResponse<Void> setAllLastMessagesToSeen(Person person, String chatUuid) {
        List<Message> messages = messageRepository.findMessagesByChat_UuidAndAndSeenAndPersonIsNot(chatUuid, false, person);

        messages.forEach(message -> {
            message.setSeen(true);
            messageRepository.save(message);
        });

        return new ServiceResponse<>(null, "State of messages was changed", ServiceResponse.OK);
    }
}
