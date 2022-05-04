package com.evg.teachingassistant.config;

import com.evg.teachingassistant.tg.BotCore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi buildTelegramBot(BotCore botCore) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(botCore);
        return telegramBotsApi;
    }
}
