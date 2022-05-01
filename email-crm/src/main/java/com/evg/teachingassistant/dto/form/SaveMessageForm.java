package com.evg.teachingassistant.dto.form;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaveMessageForm {
    private String messageId;
    private String subject;
    private String from;
    private String to;
    private Date date;
    private String body;
    private List<String> attachments;
}
