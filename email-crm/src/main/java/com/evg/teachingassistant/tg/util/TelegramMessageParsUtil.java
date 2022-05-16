package com.evg.teachingassistant.tg.util;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class TelegramMessageParsUtil {

    private final Logger logger;

    public TelegramMessageParsUtil(Logger logger) {
        this.logger = logger;
    }

    public String getSubject(Message message) {
        String messageText = message.getText();
        String trim = messageText.substring(messageText.indexOf("Тема:") + 5, messageText.indexOf("Кому:")).trim();
        logger.info("getSubject: {}", trim);
        return trim;
    }

    public String getTo(Message message) {
        String messageText = message.getText();
        String trim = messageText.substring(messageText.indexOf("Кому:") + 5, messageText.indexOf("Сообщение:")).trim();
        logger.info("getTo: {}", trim);
        return trim;
    }

    public String getContent(Message message) {
        String messageText = message.getText();
        String trim = messageText.substring(messageText.indexOf("Сообщение:") + 10).trim();
        logger.info("getContent: {}", trim);
        return trim;
    }

    public Map<String, String> getFiles(Message message) {
        return Map.of(message.getDocument().getFileId(), message.getDocument().getFileName());
    }
}
