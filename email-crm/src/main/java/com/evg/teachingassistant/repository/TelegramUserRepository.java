package com.evg.teachingassistant.repository;

import com.evg.teachingassistant.tg.model.TelegramUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TelegramUserRepository extends MongoRepository<TelegramUser, UUID> {
    Optional<TelegramUser> findByChatId(Long chatId);
}
