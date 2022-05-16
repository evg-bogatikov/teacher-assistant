package com.evg.teachingassistant.tg.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpHandler {

    public SendMessage handler(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText("Для отправки сообщения используйте такой формат: \"Тема: название_темы Кому: почта_кому_отправляете " +
                "Сообщение: содержимое_сообщения\" ");
        return sendMessage;
    }
}
