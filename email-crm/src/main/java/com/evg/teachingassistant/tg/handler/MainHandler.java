package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.tg.util.CommandParsUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainHandler {

    private final CommandParsUtil commandParsUtil;
    private final TeachersHandler teachersHandler;
    private final HelpHandler helpHandler;
    private final SettingHandler settingsHandler;
    private final StartHandler startHandler;
    private final MessageHandler messageHandler;

    public MainHandler(CommandParsUtil commandParsUtil, TeachersHandler teachersHandler, HelpHandler helpHandler, SettingHandler settingsHandler, StartHandler startHandler, MessageHandler messageHandler) {
        this.commandParsUtil = commandParsUtil;
        this.teachersHandler = teachersHandler;
        this.helpHandler = helpHandler;
        this.settingsHandler = settingsHandler;
        this.startHandler = startHandler;
        this.messageHandler = messageHandler;
    }

    public SendMessage handler(Update update) {
        if (update.hasMessage()) {
            switch (commandParsUtil.getCommand(update.getMessage().getText())) {
                case START:
                    return startHandler.startBot(update.getMessage());
                case HELP:
                    return helpHandler.getInfo(update.getMessage());
                case SETTINGS:
                    return settingsHandler.getSettings(update.getMessage());
                case SETTINGS_UPDATE:
                    return settingsHandler.updateSettings(update.getMessage());
                case TEACHERS:
                    return teachersHandler.getAllTeachers(update.getMessage());
                case MESSAGE:
                    return messageHandler.getAllMessageByChatId(update.getMessage());
                case MESSAGE_SEND:
                    return messageHandler.sendMessageToTeachers(update.getMessage());
            }
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Command Not Found");
        return sendMessage;
    }
}
