package com.justibot.backend.util;

import java.util.UUID;

public class ChatIdGenerator {
    public static String generateChatId() {
        return UUID.randomUUID().toString();
    }
}