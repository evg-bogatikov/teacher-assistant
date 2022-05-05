package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.model.Message;
import com.evg.teachingassistant.security.CustomUserDetails;
import com.evg.teachingassistant.service.api.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessage() {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getAllMessageByUserId(userId));
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID emailId) {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getMessageById(emailId));
    }

    @PostMapping("/update/from-email")
    public ResponseEntity<List<Message>> getAllMessageFromEmailBox() {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(messageService.getMessageFromEmailBox(userId));
    }

    @GetMapping("/{messageId}/upload/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID messageId, @PathVariable String fileId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.ALL);
        byte[] file = messageService.getFile(messageId, fileId);
        return ResponseEntity.ok()
                .contentLength(file.length)
                .contentType(MediaType.ALL)
                .body(file);
    }
}
