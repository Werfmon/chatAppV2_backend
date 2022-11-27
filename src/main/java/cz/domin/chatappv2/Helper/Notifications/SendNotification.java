package cz.domin.chatappv2.Helper.Notifications;

import com.google.gson.Gson;
import cz.domin.chatappv2.Controller.dto.create.FCM.FCMDataPart;
import cz.domin.chatappv2.Controller.dto.create.FCM.FCMNotificationPart;
import cz.domin.chatappv2.Controller.dto.create.FCM.FCMRequestDTO;
import cz.domin.chatappv2.Helper.Response.ServiceResponse;
import cz.domin.chatappv2.Model.Chat;
import cz.domin.chatappv2.Model.Person;
import cz.domin.chatappv2.Service.ChatService;
import cz.domin.chatappv2.Service.PersonSubscribeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Component
public class SendNotification {
    private final String FIREBASE_SERVER_KEY = "AAAAstXSwbM:APA91bGeiNRPXMFflKnMYxAwj36Q3EV2r7pmeiOZWb02AZJrsbA1-2DqncdkZb1j54uNFoE_fi7F_6tPHWoQwr_kbls01ESj8hU2ze1LaYz7qDLSQ7uTgyWKPIAeQZtm46ezHVsL4qWQ";

    private final ChatService chatService;
    private final PersonSubscribeService personSubscribeService;

    public void sendNotification(String chatUuid, String personUuid, TextMessage message) {
        ServiceResponse<Chat> serviceResponseChat = chatService.findChat(chatUuid);
        log.info("Notification will be");
        if (serviceResponseChat.getStatus() == ServiceResponse.OK) {
            Person friend;
            Person person;
            if (Objects.equals(serviceResponseChat.getData().getFriendship().getMainPerson().getUuid(), personUuid)) {
                friend = serviceResponseChat.getData().getFriendship().getPerson();
                person = serviceResponseChat.getData().getFriendship().getMainPerson();
            } else {
                friend = serviceResponseChat.getData().getFriendship().getMainPerson();
                person = serviceResponseChat.getData().getFriendship().getPerson();
            }
            log.info("Friend was found");

            ServiceResponse<String> serviceResponseFCMToken = personSubscribeService.getTokenByPerson(friend);

            if (serviceResponseFCMToken.getStatus() == ServiceResponse.OK) {
                String title = String.format("%s %s (%s)", person.getFirstName(), person.getLastName(), person.getNickname());
                String body = message.getPayload();
                String FCMToken = serviceResponseFCMToken.getData();

                FCMNotificationPart fcmNotificationPart = new FCMNotificationPart();
                fcmNotificationPart.setBody(body);
                fcmNotificationPart.setTitle(title);

                FCMDataPart fcmDataPart = new FCMDataPart();
                fcmDataPart.setPersonUuid(personUuid);
                fcmDataPart.setSentDate(LocalDateTime.now().toString());

                FCMRequestDTO fcmRequestDTO = new FCMRequestDTO();
                fcmRequestDTO.setTo(FCMToken);
                fcmRequestDTO.setNotification(fcmNotificationPart);
                fcmRequestDTO.setData(fcmDataPart);

                String convertedBody = new Gson().toJson(fcmRequestDTO);
                log.info("Send to google api: " + convertedBody);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", "key=" + FIREBASE_SERVER_KEY);

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://fcm.googleapis.com/fcm/send");

                HttpEntity<?> entity = new HttpEntity<>(convertedBody, headers);

                RestTemplate restTemplate = new RestTemplate();

                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.POST,
                        entity,
                        String.class);
            }
        } else {
            log.warn(serviceResponseChat.getMessage());
        }
    }
}
