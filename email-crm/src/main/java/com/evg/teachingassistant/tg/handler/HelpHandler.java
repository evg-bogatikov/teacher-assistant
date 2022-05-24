package com.evg.teachingassistant.tg.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpHandler {

    public SendMessage getInfo(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText("Для отправки сообщения используйте такой формат: /message_send Тема: название_темы Кому: почта_кому_отправляете " +
                "Сообщение: содержимое_сообщения \n" +
                "Для обновления настроек используйте формат: /settings_update: Имя: имя Фамилия: фамилия Группа: название_группы");
        return sendMessage;
    }
}
