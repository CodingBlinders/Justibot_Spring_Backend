package com.justibot.backend.model;

public class MessageResponse {
    private String message;
    private String chatId;
    private String response;

    // Constructors
    public MessageResponse(String message, String chatId, String response) {
        this.message = message;
        this.chatId = chatId;
        this.response = response;
    }

    public MessageResponse() {
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getChatId() {
        return chatId;
    }

    public String getResponse() {
        return response;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
