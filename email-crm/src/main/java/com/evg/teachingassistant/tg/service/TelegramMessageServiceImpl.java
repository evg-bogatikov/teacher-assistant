package com.evg.teachingassistant.tg.service;

import com.evg.teachingassistant.dto.form.SaveMessageForm;
import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.MessageType;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.service.api.MessageService;
import com.evg.teachingassistant.service.api.UserService;
import com.evg.teachingassistant.tg.model.TelegramUser;
import com.evg.teachingassistant.tg.service.api.TelegramMessageService;
import com.evg.teachingassistant.tg.service.api.TelegramUserService;
import com.evg.teachingassistant.tg.util.TelegramMessageParsUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    private final MessageService messageService;
    private final TelegramUserService telegramUserService;
    private final UserService userService;

    private final TelegramMessageParsUtil telegramMessageParsUtil;

    public TelegramMessageServiceImpl(MessageService messageService, TelegramUserService telegramUserService, UserService userService, TelegramMessageParsUtil telegramMessageParsUtil) {
        this.messageService = messageService;
        this.telegramUserService = telegramUserService;
        this.userService = userService;
        this.telegramMessageParsUtil = telegramMessageParsUtil;
    }

    @Override
    public SendMessage getAllMessageByChatId(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        TelegramUser telegramUserByChatId = telegramUserService.getTelegramUserByChatId(message.getChatId());
        if (telegramUserByChatId.getMessages().size() == 0) {
            sendMessage.setText("Вы ещё не отправили ни одного сообщения!");
            return sendMessage;
        }
        List<com.evg.teachingassistant.model.Message> messages = messageService.getAllMessageByListId(telegramUserService.getTelegramUserByChatId(message.getChatId()).getMessages())
                .orElseThrow(EntityNotFoundException::new);

        String allMessageByChatId = mapMessageToString(messages);
        sendMessage.setText(allMessageByChatId);
        return sendMessage;
    }

    @Override
    public SendMessage sendMessageToTeacher(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        SaveMessageForm saveMessageForm = mapTelegramMessageToSaveMessageForm(message);
        saveMessageForm.setAttachments(new ArrayList<>());

        TelegramUser telegramUserByChatId = telegramUserService.getTelegramUserByChatId(message.getChatId());
        saveMessageForm.setFrom(telegramUserByChatId.getFirstName() + " " + telegramUserByChatId.getLastName());

        User user = userService.getUserByEmail(saveMessageForm.getTo())
                .orElseThrow(EntityNotFoundException::new);

        com.evg.teachingassistant.model.Message message1 = messageService.saveMassage(saveMessageForm, user.getId());

        telegramUserService.addMessage(telegramUserByChatId.getId(), message1.getId());

        sendMessage.setText("Message sent!");
        return sendMessage;
    }

    private String mapMessageToString(List<com.evg.teachingassistant.model.Message> messages) {
        return messages
                .stream()
                .map(message -> String.format("Тема: %s; Кому: %s; Сообщение: %s\n", message.getSubject(), message.getTo(), message.getContent()))
                .collect(Collectors.joining(""));
    }

    private SaveMessageForm mapTelegramMessageToSaveMessageForm(Message telegramMessage) {
        SaveMessageForm saveMessageForm = new SaveMessageForm();
        saveMessageForm.setSubject(telegramMessageParsUtil.getSubject(telegramMessage));
        saveMessageForm.setTo(telegramMessageParsUtil.getTo(telegramMessage));
        saveMessageForm.setDate(new Date(telegramMessage.getDate()));
        saveMessageForm.setBody(telegramMessageParsUtil.getContent(telegramMessage));
        saveMessageForm.setCategories(List.of());
        saveMessageForm.setMessageType(MessageType.MESSANGER);

        return saveMessageForm;
    }
}
