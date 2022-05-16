package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.dto.form.SaveMessageForm;
import com.evg.teachingassistant.dto.form.SendMessageForm;
import com.evg.teachingassistant.model.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    List<Message> getAllMessageByUserId(UUID userId);

    Message getMessageById(UUID messageId);

    Message saveMassage(SaveMessageForm saveMessageForm, UUID userId);

    List<Message> saveAllMessage(List<SaveMessageForm> saveMessageFormList, UUID userId);

    List<Message> getMessageFromEmailBox(UUID userId);

    Void sendEmail(SendMessageForm messageForm, UUID userId);

    Optional<List<Message>> getAllMessageByListId(List<UUID> messageId);

}
