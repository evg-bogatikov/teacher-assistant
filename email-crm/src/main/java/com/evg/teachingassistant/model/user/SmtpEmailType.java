package com.evg.teachingassistant.model.user;

public enum SmtpEmailType {
    GOOGLE("smtp.google.ru"),
    MAIL("smtp.mail.ru"),
    YANDEX("smtp.yandex.ru");

    private final String host;

    SmtpEmailType(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
