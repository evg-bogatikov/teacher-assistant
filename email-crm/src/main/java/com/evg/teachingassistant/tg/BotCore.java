package com.evg.teachingassistant.tg;

import com.evg.teachingassistant.tg.handler.TaskHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotCore extends TelegramLongPollingBot {
    private final TaskHandler taskHandler;

    @Value("${telegram.bot.token}")
    private String TOKEN;

    @Value("${telegram.bot.name}")
    private String NAME;

    public BotCore(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasPhoto()){
           SendMessage sendMessage = new SendMessage();
           sendMessage.setChatId(update.getMessage().getChatId().toString());

        }
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            SendMessage message = taskHandler.handleTask(update);

            try {
                execute(message);
            } catch (TelegramApiException telegramApiException) {
                telegramApiException.printStackTrace();
            }
        }
    }
}
