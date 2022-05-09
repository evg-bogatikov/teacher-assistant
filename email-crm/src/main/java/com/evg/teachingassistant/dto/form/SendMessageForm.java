package com.evg.teachingassistant.dto.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendMessageForm {
    @NotBlank
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
