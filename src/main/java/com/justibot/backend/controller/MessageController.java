package com.justibot.backend.controller;

import com.justibot.backend.dto.MessageRequest;
import com.justibot.backend.service.MessageService;
import com.justibot.backend.util.ChatIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        String chatId = messageRequest.getChatId();
        String message = messageRequest.getMessage();

        if (chatId == null || chatId.isEmpty()) {
            chatId = ChatIdGenerator.generateChatId();
        }

        return messageService.forwardMessage(chatId, message);
    }
}
