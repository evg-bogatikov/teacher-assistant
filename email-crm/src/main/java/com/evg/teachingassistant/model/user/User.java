package com.evg.teachingassistant.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection="usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private UUID id;
    private String name;
    private String password;
    private String email;
    private TypeEmail typeEmail;
    private String appPassword;
    private List<Role> roles;
}

