package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.tg.service.api.TelegramSettingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SettingHandler {

    private final TelegramSettingService telegramSettingService;

    public SettingHandler(TelegramSettingService telegramSettingService) {
        this.telegramSettingService = telegramSettingService;
    }

    public SendMessage getSettings(Message message) {
        return telegramSettingService.getSettings(message);
    }

    public SendMessage updateSettings(Message message) {
        return telegramSettingService.updateSettings(message);
    }
}
