package com.evg.teachingassistant.model.user;

public enum TypeEmail {
    GOOGLE("imap.google.ru"),
    MAIL("imap.mail.ru"),
    YANDEX("imap.yandex.ru");

    private final String host;

    TypeEmail(String host){
        this.host = host;
    }

    public String getHost(){
        return host;
    }
}
