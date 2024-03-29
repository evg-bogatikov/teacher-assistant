package com.evg.teachingassistant.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserView {
    UUID id;
    String firstName;
    String lastName;
    String email;
}
