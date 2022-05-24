package com.evg.teachingassistant.tg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {
    private UUID id;
    private String firstName;
    private String lastName;
    private String group;
    private List<UUID> messages;
    private Long chatId;
}
