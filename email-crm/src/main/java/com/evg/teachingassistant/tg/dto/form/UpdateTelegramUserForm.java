package com.evg.teachingassistant.tg.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTelegramUserForm {
   private String firstName;
   private String lastName;
   private String group;
}
