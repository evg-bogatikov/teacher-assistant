package com.evg.teachingassistant.tg.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TaskHandler {

    public SendMessage handleTask(Update update) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        saveFile(update.getMessage().getDocument());
        sendMessage.setText("File send for !");
        return sendMessage;
    }

    public boolean saveFile(Document document) {
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        System.out.println(getFile.getFileId());
        return true;
    }


}
