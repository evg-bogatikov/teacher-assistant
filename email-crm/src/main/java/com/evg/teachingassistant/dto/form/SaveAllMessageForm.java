package com.evg.teachingassistant.dto.form;

import com.evg.teachingassistant.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class SaveAllMessageForm {
    private List<SaveMessageForm> messageList;
}
