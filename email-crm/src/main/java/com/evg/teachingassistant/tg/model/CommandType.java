package com.evg.teachingassistant.tg.model;

public enum CommandType {
    START("/start"),
    HELP("/help"),
    TEACHERS("/teachers"),
    SETTINGS("/settings"),
    SETTINGS_UPDATE("/settings_update"),
    MESSAGE("/message"),
    MESSAGE_SEND("/message_send");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
