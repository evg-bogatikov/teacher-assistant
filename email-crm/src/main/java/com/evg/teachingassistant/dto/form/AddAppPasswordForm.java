package com.evg.teachingassistant.dto.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddAppPasswordForm {
    @NotBlank
    String userId;
    @NotBlank
    String appPassword;
}
