package com.evg.teachingassistant.tg.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class TelegramMessageParsUtil {

    public String getSubject(Message message) {
        String messageText = message.getText();
        return messageText.substring(messageText.indexOf("Тема:") + 5, messageText.indexOf("Кому:")).trim();
    }

    public String getTo(Message message) {
        String messageText = message.getText();
        return messageText.substring(messageText.indexOf("Кому:") + 5, messageText.indexOf("Сообщение:")).trim();
    }

    public String getContent(Message message) {
        String messageText = message.getText();
        return messageText.substring(messageText.indexOf("Сообщение:") + 10).trim();
    }

    public Map<String, String> getFiles(Message message) {
        return Map.of(message.getDocument().getFileId(), message.getDocument().getFileName());
    }
}
