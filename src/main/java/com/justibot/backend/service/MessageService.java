package com.justibot.backend.service;

import com.justibot.backend.model.Chat;
import com.justibot.backend.model.MessageResponse;
import com.justibot.backend.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class MessageService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Autowired
    private ChatRepository chatRepository;
    private final RestTemplate restTemplate;

    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String forwardMessage(String chatId, String message, String UserId) {
        // Create the payload
        Map<String, String> payload = new HashMap<>();
        payload.put("session_id", chatId);
        payload.put("message", message);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payload, headers);

        // Send the request and get the response
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                externalApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

//        String response = "dummy response";
        String response = responseEntity.getBody();
        Chat chat = chatRepository.findByChatId(chatId);
        if (chat == null) {
            chat = new Chat();
            chat.setChatTitle("Chat");
            chat.setUserId(UserId);
            chat.setChatId(chatId);
        }
        else if(!Objects.equals(UserId, chat.getUserId())){
            response = "You are not authorized to send messages to this chat";
            return response;
        }

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(message);
        messageResponse.setResponse(response);
        chat.getMessages().add(messageResponse);

        chatRepository.save(chat);
        String responseText = responseEntity.getBody();

        assert responseText != null;
        responseText = responseText.substring(1, responseText.length() - 1);
        responseText = responseText.replace("\\n", "\n");

        return responseText;
    }
}
