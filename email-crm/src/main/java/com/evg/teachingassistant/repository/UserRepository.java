package com.evg.teachingassistant.repository;

import com.evg.teachingassistant.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
}
