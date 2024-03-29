package com.evg.teachingassistant.dto.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAuthForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
