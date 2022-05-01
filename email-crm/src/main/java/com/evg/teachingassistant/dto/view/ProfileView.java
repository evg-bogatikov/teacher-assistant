package com.evg.teachingassistant.dto.view;

import com.evg.teachingassistant.model.user.TypeEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProfileView {
    UUID id;
    String name;
    String email;
    TypeEmail typeEmail;
}
