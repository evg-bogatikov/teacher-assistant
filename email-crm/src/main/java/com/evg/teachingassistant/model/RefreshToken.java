package com.evg.teachingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class RefreshToken {
    private UUID id;
    private String username;
    private String token;
    private Instant expiryDate;
}
