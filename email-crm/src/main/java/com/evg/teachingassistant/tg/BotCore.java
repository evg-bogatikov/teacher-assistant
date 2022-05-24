package com.evg.teachingassistant.tg;

import com.evg.teachingassistant.tg.handler.MainHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotCore extends TelegramLongPollingBot {

    private final MainHandler mainHandler;

    @Value("${telegram.bot.token}")
    private String TOKEN;

    @Value("${telegram.bot.name}")
    private String NAME;

    public BotCore(MainHandler mainHandler) {
        this.mainHandler = mainHandler;
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
        try {
            execute(mainHandler.handler(update));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
