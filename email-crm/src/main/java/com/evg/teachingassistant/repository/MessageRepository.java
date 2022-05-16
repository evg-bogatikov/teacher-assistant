package com.evg.teachingassistant.repository;

import com.evg.teachingassistant.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {
    Optional<List<Message>> findAllByUserId(UUID userId);
    Optional<List<Message>> findAllByIdIn(List<UUID> messageId);
}
