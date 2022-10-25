package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Controller.dto.read.ReadChatDTO;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertor;
import cz.domin.chatappv2.Helper.Convertor.Base64ImageConvertorResponse;
import cz.domin.chatappv2.Helper.Response.Response;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.*;
import cz.domin.chatappv2.Repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final FriendshipStatusService friendshipStatusService;

    public ServiceResponse<List<ReadChatDTO>> getPersonChats(String uuid) {
         Person person = personService.getPersonByUuid(uuid);

         List<Chat> chats = chatRepository.findChatsByFriendship_MainPersonOrFriendship_PersonAndFriendship_Status(
                 person,
                 person,
                 friendshipStatusService.getById(FriendshipStatus.ACCEPTED)
         );

         List<ReadChatDTO> readChatDTOS = chats.stream().map(c -> {
             ReadChatDTO readChatDTO = modelMapper.map(c, ReadChatDTO.class);
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

}