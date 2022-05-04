package com.evg.teachingassistant.repository;

import com.evg.teachingassistant.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
}
