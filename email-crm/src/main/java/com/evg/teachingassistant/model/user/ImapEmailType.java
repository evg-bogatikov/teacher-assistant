package com.evg.teachingassistant.model.user;

public enum ImapEmailType {
    GOOGLE("imap.google.ru"),
    MAIL("imap.mail.ru"),
    YANDEX("imap.yandex.ru");

    private final String host;

    ImapEmailType(String host){
        this.host = host;
    }

    public String getHost(){
        return host;
    }
}
