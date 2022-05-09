package com.evg.teachingassistant.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="usr")
public class User {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private TypeImapEmail typeImapEmail;
    private TypeSmtpEmail typeSmtpEmail;
    private String appPassword;
    private Set<Role> roles;
}

