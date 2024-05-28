package com.justibot.backend.repository;

import com.justibot.backend.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Chat findByChatId(String chatId);
}
