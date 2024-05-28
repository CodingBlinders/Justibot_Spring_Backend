package com.justibot.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "chats")
public class Chat {

    @Id
    private String id;
    private String userId;
    private String chatId;
    private String title;
    private String chatModel;
    private List<MessageResponse> messages = new ArrayList<>();

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    public void getChatId(String chatId) {
        this.chatId = chatId;
    }

    public Collection<MessageResponse> getMessages() {
        return messages;
    }

    public void setChatTitle(String chat) {
        this.title = chat;
    }

    public void setUserId(String user) {
        this.userId = user;
    }

    public String getUserId() {
        return userId;
    }
}

