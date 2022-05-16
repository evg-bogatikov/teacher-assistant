package com.evg.teachingassistant.tg.service.api;

import com.evg.teachingassistant.tg.dto.view.TelegramProfile;
import com.evg.teachingassistant.tg.dto.form.SaveTelegramUserForm;
import com.evg.teachingassistant.tg.dto.form.UpdateTelegramUserForm;
import com.evg.teachingassistant.tg.model.TelegramUser;

import java.util.UUID;

public interface TelegramUserService {
    void saveTelegramUser(SaveTelegramUserForm saveTelegramUserForm);
    TelegramProfile updateTelegramUser(UpdateTelegramUserForm updateTelegramUserForm, Long chatId);
    TelegramProfile getTelegramProfileByChatId(Long chatId);

    TelegramUser getTelegramUserByChatId(Long chatId);

    void addMessage(UUID telegramUserId, UUID messageId);
}
