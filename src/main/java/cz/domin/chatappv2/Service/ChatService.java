package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.read.ReadChatDTO;
import cz.domin.chatappv2.Controller.dto.read.ReadFriendshipDTO;
import cz.domin.chatappv2.Controller.dto.read.ReadPersonDTO;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertor;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertorResponse;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.*;
import cz.domin.chatappv2.Repository.ChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;


    public ServiceResponse<List<ReadChatDTO>> getPersonChats(String uuid, String searchText, int limit, int offset) {
         List<Chat> chats = chatRepository.findChatsBy(
                 uuid,
                 FriendshipStatus.ACCEPTED,
                 "%" + searchText + "%",
                 limit,
                 offset
         );

         List<ReadChatDTO> readChatDTOS = chats.stream().map(c -> {

             ReadChatDTO readChatDTO = modelMapper.map(c, ReadChatDTO.class);
             ReadFriendshipDTO readFriendshipDTO = modelMapper.map(c.getFriendship(), ReadFriendshipDTO.class);
             ReadPersonDTO personDTO = modelMapper.map(c.getFriendship().getPerson(), ReadPersonDTO.class);
             ReadPersonDTO mainPersonDTO = modelMapper.map(c.getFriendship().getMainPerson(), ReadPersonDTO.class);

             readFriendshipDTO.setPerson(personDTO);
             readFriendshipDTO.setMainPerson(mainPersonDTO);
             readChatDTO.setFriendship(readFriendshipDTO);

             String imagePath;
             Base64ImageConvertorResponse base64ImageConvertorResponse;
             imagePath = c.getFriendship().getMainPerson().getImagePath();
             base64ImageConvertorResponse = Base64ImageConvertor.load(imagePath);

             if (!base64ImageConvertorResponse.isStatus()) {
                 return null;
             }

             readChatDTO.getFriendship().getMainPerson().setBase64Image(base64ImageConvertorResponse.getResponse());

             imagePath = c.getFriendship().getPerson().getImagePath();
             base64ImageConvertorResponse = Base64ImageConvertor.load(imagePath);

             if (!base64ImageConvertorResponse.isStatus()) {
                 return null;
             }

             readChatDTO.getFriendship().getPerson().setBase64Image(base64ImageConvertorResponse.getResponse());

             return readChatDTO;
         }).toList();

        return new ServiceResponse<>(readChatDTOS, "Returned user`s chat", ServiceResponse.OK);
    }
    public ServiceResponse<Chat> findChat(String chatUuid) {
        Chat chat = chatRepository.findById(chatUuid).orElse(null);

        if (chat == null) {
            return new ServiceResponse<>(null, "Chat not found", ServiceResponse.ERROR);
        }
        return new ServiceResponse<>(chat, "Chat was found", ServiceResponse.OK);
    }
}