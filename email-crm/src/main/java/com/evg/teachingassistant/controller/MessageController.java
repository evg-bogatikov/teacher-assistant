package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.model.Message;
import com.evg.teachingassistant.security.CustomUserDetails;
import com.evg.teachingassistant.service.api.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessage() {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getAllMessageByUserId(userId));
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{emailId}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID emailId) {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getMessageById(emailId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/from-email")
    public ResponseEntity<List<Message>> getAllMessageFromEmailBox() {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getMessageFromEmailBox(userId));
    }
}
