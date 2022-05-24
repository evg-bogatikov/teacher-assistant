package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.tg.service.api.TelegramMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler {

    private final TelegramMessageService telegramMessageService;

    public MessageHandler(TelegramMessageService telegramMessageService) {
        this.telegramMessageService = telegramMessageService;
    }

    public SendMessage sendMessageToTeachers(Message message) {
        return telegramMessageService.sendMessageToTeacher(message);
    }

    public SendMessage getAllMessageByChatId(Message message) {
        return telegramMessageService.getAllMessageByChatId(message);
    }
}
