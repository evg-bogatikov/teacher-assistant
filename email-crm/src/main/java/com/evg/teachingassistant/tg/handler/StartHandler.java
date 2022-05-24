package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.tg.dto.form.SaveTelegramUserForm;
import com.evg.teachingassistant.tg.service.api.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartHandler {
    private final TelegramUserService telegramUserService;

    public StartHandler(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    public SendMessage startBot(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        telegramUserService.saveTelegramUser(new SaveTelegramUserForm(
                message.getFrom().getFirstName(),
                message.getFrom().getLastName(),
                "",
                message.getChatId()
        ));

        sendMessage.setText("Здравствуйте, поздравляем, вы можете использовать нашего бота!\n" +
                "Не забудьте проверить настройки!");
        return sendMessage;
    }
}
