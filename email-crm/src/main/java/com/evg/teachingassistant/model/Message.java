package com.evg.teachingassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Message {
    @Id
    private UUID id;
    private String subject;
    private String from;
    private String to;
    private Date date;
    private String content;
    private Map<String, String> file;//fileId & fileName
    private MessageType messageType;
    private UUID userId;
}
