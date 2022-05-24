package com.evg.teachingassistant.tg.service;

import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.repository.TelegramUserRepository;
import com.evg.teachingassistant.tg.dto.form.SaveTelegramUserForm;
import com.evg.teachingassistant.tg.dto.form.UpdateTelegramUserForm;
import com.evg.teachingassistant.tg.dto.view.TelegramProfile;
import com.evg.teachingassistant.tg.model.TelegramUser;
import com.evg.teachingassistant.tg.service.api.TelegramUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;

    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }


    @Override
    public void saveTelegramUser(SaveTelegramUserForm saveTelegramUserForm) {
        telegramUserRepository.save(new TelegramUser(
                UUID.randomUUID(),
                saveTelegramUserForm.getFirstName(),
                saveTelegramUserForm.getLastName(),
                saveTelegramUserForm.getGroup(),
                List.of(),
                saveTelegramUserForm.getChatId()
        ));
    }

    @Override
    public TelegramProfile updateTelegramUser(UpdateTelegramUserForm updateTelegramUserForm, Long chatId) {
        TelegramUser telegramUser = telegramUserRepository.findByChatId(chatId)
                .orElseThrow(EntityNotFoundException::new);
        telegramUser.setFirstName(updateTelegramUserForm.getFirstName());
        telegramUser.setLastName(updateTelegramUserForm.getLastName());
        telegramUser.setGroup(updateTelegramUserForm.getGroup());
        TelegramUser save = telegramUserRepository.save(telegramUser);
        return mapTelegramUserToTelegramProfile(save);
    }

    @Override
    public TelegramProfile getTelegramProfileByChatId(Long chatId) {
        TelegramUser telegramUser = telegramUserRepository.findByChatId(chatId)
                .orElseThrow(EntityNotFoundException::new);
        return mapTelegramUserToTelegramProfile(telegramUser);
    }

    @Override
    public TelegramUser getTelegramUserByChatId(Long chatId) {
        return telegramUserRepository.findByChatId(chatId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addMessage(UUID telegramUserId, UUID messageId) {
        TelegramUser telegramUser = telegramUserRepository.findById(telegramUserId)
                .orElseThrow(EntityNotFoundException::new);
        telegramUser.getMessages().add(messageId);
        telegramUserRepository.save(telegramUser);
    }

    private TelegramProfile mapTelegramUserToTelegramProfile(TelegramUser telegramUser) {
        return new TelegramProfile(
                telegramUser.getFirstName(),
                telegramUser.getLastName(),
                telegramUser.getGroup()
        );
    }
}
