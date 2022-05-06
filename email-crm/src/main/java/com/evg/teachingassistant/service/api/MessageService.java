package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.dto.form.SaveMessageForm;
import com.evg.teachingassistant.model.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<Message> getAllMessageByUserId(UUID userId);

    Message getMessageById(UUID letterId);

    Message saveMassage(SaveMessageForm saveMessageForm, UUID userId);

    List<Message> saveAllMessage(List<SaveMessageForm> saveMessageFormList, UUID userId);

    List<Message> getMessageFromEmailBox(UUID userId);

//    Boolean sendEmail(Message message);

}
