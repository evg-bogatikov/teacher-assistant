package com.evg.teachingassistant.dto.form;

import com.evg.teachingassistant.model.user.TypeEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SaveUserForm {
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private TypeEmail typeEmail;
    @NotEmpty
    private String appPassword;
}
