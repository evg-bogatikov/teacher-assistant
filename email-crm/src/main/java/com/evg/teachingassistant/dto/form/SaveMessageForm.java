package com.evg.teachingassistant.dto.form;

import com.evg.teachingassistant.model.MessageType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaveMessageForm {
    private String subject;
    private String from;
    private String to;
    private Date date;
    private String body;
    private List<String> categories;
    private MessageType messageType = MessageType.MAIL;
    private List<FileForm> attachments;
}
