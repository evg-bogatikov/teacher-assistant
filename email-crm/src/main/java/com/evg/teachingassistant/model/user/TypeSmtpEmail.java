package com.evg.teachingassistant.model.user;

public enum TypeSmtpEmail {
    GOOGLE("smtp.google.ru"),
    MAIL("smtp.mail.ru"),
    YANDEX("smtp.yandex.ru");

    private final String host;

    TypeSmtpEmail(String host){
        this.host = host;
    }

    public String getHost(){
        return host;
    }
}
