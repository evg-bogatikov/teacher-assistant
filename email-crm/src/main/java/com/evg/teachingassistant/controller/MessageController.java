package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.model.Message;
import com.evg.teachingassistant.service.api.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessage() {
        return ResponseEntity.ok(messageService.getAllMessageByUserId(UUID.randomUUID()));
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID emailId) {
        return ResponseEntity.ok(messageService.getMessageById(emailId));
    }

    @PostMapping("/update/from-email")
    public ResponseEntity<List<Message>> getAllMessageFromEmailBox() {
        return ResponseEntity.ok(messageService.getMessageFromEmailBox(UUID.fromString("9d2f7b38-1a43-4a2d-8975-3ff2aa02d05f")));
    }
}
