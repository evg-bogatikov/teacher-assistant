package com.evg.teachingassistant.tg.service.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramMessageService {
    SendMessage getAllMessageByChatId(Message message);

    SendMessage sendMessageToTeacher(Message message);

}
