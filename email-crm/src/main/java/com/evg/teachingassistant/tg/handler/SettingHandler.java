package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.tg.dto.view.TelegramProfile;
import com.evg.teachingassistant.tg.dto.form.UpdateTelegramUserForm;
import com.evg.teachingassistant.tg.service.api.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SettingHandler {

    private final TelegramUserService telegramUserService;

    public SettingHandler(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    public SendMessage getSettings(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        TelegramProfile telegramProfileByChatId = telegramUserService.getTelegramProfileByChatId(message.getChatId());
        sendMessage.setText(String.format("Имя: %s; Фамилия: %s, Группа: %s", telegramProfileByChatId.getFirstName(),
                telegramProfileByChatId.getLastName(), telegramProfileByChatId.getGroup()));
        return sendMessage;
    }

    public SendMessage updateSettings(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        String messageText = message.getText();

        TelegramProfile telegramProfileByChatId = telegramUserService.updateTelegramUser(new UpdateTelegramUserForm(
                parseFirstName(messageText),
                parseLastName(messageText),
                parseGroup(messageText)
        ), message.getChatId());
        sendMessage.setText(String.format("Имя: %s; Фамилия: %s, Группа: %s", telegramProfileByChatId.getFirstName(),
                telegramProfileByChatId.getLastName(), telegramProfileByChatId.getGroup()));
        return sendMessage;
    }

    private String parseFirstName(String messageText) {
        return messageText.substring(messageText.indexOf("Имя:") + 4, messageText.indexOf("Фамилия:")).trim();
    }

    private String parseLastName(String messageText) {
        return messageText.substring(messageText.indexOf("Фамилия:") + 8, messageText.indexOf("Группа:")).trim();
    }

    private String parseGroup(String messageText) {
        return messageText.substring(messageText.indexOf("Группа:") + 8).trim();
    }
}
