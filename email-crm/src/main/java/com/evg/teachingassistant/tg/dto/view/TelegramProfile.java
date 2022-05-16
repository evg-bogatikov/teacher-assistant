package com.evg.teachingassistant.tg.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class TelegramProfile {
    private String firstName;
    private String lastName;
    private String group;
}
