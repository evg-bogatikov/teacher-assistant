package com.evg.teachingassistant.tg.handler;

import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeachersHandler {
    private final UserService userService;

    public TeachersHandler(UserService userService) {
        this.userService = userService;
    }

    public SendMessage getAllTeachers(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(getAllTeachers());
        return sendMessage;
    }

    public String getAllTeachers() {
        List<UserView> userViews = userService.getAllUser();
        return userViews.stream()
                .map(userView -> String.format("Имя: %s, Фамилия: %s, Почта: %s\n",
                        userView.getFirstName(), userView.getLastName(), userView.getEmail()))
                .collect(Collectors.joining(""));
    }
}
