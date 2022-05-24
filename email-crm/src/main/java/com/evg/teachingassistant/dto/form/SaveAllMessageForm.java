package com.evg.teachingassistant.dto.form;

import lombok.Data;

import java.util.List;

@Data
public class SaveAllMessageForm {
    private List<SaveMessageForm> messageList;
}
