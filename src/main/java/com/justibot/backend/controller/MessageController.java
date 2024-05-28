package com.justibot.backend.controller;

import com.justibot.backend.dto.MessageRequest;
import com.justibot.backend.model.MessageResponse;
import com.justibot.backend.model.User;
import com.justibot.backend.repository.UserRepository;
import com.justibot.backend.service.MessageService;
import com.justibot.backend.service.UserService;
import com.justibot.backend.util.ChatIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/sendMessage", produces = "application/json")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String chatId = messageRequest.getChatId();
        String message = messageRequest.getMessage();

        String userName = userDetails.getUsername();
        User user = userRepository.findByUsername(userName);

        if (chatId == null || chatId.isEmpty()) {
            chatId = ChatIdGenerator.generateChatId();
        }

        String response = messageService.forwardMessage(chatId, message, user.getId());
        System.out.println(response);

        MessageResponse messageResponse = new MessageResponse(message, chatId, response);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
