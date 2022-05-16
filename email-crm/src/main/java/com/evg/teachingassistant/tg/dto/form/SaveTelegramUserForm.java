package com.evg.teachingassistant.tg.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveTelegramUserForm {
    private String firstName;
    private String lastName;
    private String group;
    private Long chatId;
}
