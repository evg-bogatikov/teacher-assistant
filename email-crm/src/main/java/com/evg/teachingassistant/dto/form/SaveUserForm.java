package com.evg.teachingassistant.dto.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class SaveUserForm {
    @NotBlank(message = "First name may not be blank")
    private String firstName;
    @NotBlank(message = "Last name may not be blank")
    private String lastName;
    @NotBlank(message = "Password may not be blank")
    private String password;
    @NotBlank(message = "Email may not be blank")
    private String email;
}
