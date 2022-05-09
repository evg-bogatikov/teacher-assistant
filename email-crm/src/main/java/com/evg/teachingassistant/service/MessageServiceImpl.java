package com.evg.teachingassistant.service;

import com.evg.teachingassistant.dto.form.FileForm;
import com.evg.teachingassistant.dto.form.SaveAllMessageForm;
import com.evg.teachingassistant.dto.form.SaveMessageForm;
import com.evg.teachingassistant.dto.form.SendMessageForm;
import com.evg.teachingassistant.exception.EntityNotContainAppPasswordException;
import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.Message;
import com.evg.teachingassistant.model.TypeMessage;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.repository.MessageRepository;
import com.evg.teachingassistant.service.api.MessageService;
import com.evg.teachingassistant.service.api.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;
    private final UserService userService;
    @Value("${url-ms.email-receiver}")
    private String urlReceiverService;

    public MessageServiceImpl(MessageRepository messageRepository, RestTemplate restTemplate, UserService userService) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @Override
    public List<Message> getAllMessageByUserId(UUID userId) {
        return messageRepository.findAllByUserId(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Message getMessageById(UUID letterId) {
        return messageRepository.findById(letterId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Message saveMassage(SaveMessageForm saveMessageForm, UUID userId) {
        return messageRepository.save(new Message(
                UUID.randomUUID(),
                saveMessageForm.getSubject(),
                saveMessageForm.getFrom(),
                saveMessageForm.getTo(),
                saveMessageForm.getDate(),
                saveMessageForm.getBody(),
                mapFileFormToMap(saveMessageForm.getAttachments()),
                TypeMessage.MAIL,
                userId
        ));
    }

    @Override
    public List<Message> saveAllMessage(List<SaveMessageForm> saveMessageFormList, UUID userId) {
        List<Message> messageList = saveMessageFormList.stream()
                .map(saveMessageForm -> {
                    return new Message(
                            UUID.randomUUID(),
                            saveMessageForm.getSubject(),
                            saveMessageForm.getFrom(),
                            saveMessageForm.getTo(),
                            saveMessageForm.getDate(),
                            saveMessageForm.getBody(),
                            mapFileFormToMap(saveMessageForm.getAttachments()),
                            TypeMessage.MAIL,
                            userId
                    );
                }).collect(Collectors.toList());

        return messageRepository.saveAll(messageList);
    }

    @Override
    public List<Message> getMessageFromEmailBox(UUID userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);
        if (user.getAppPassword().isEmpty())
            throw new EntityNotContainAppPasswordException();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", user.getEmail());
        jsonObject.put("password", user.getAppPassword());
        jsonObject.put("host", user.getTypeImapEmail().getHost());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        ResponseEntity<SaveAllMessageForm> forEntity = restTemplate.postForEntity(urlReceiverService + "/api/v1/email/", request, SaveAllMessageForm.class);
        List<SaveMessageForm> messageList = Objects.requireNonNull(forEntity.getBody()).getMessageList();
        return saveAllMessage(messageList, userId);
    }

    @Override
    public Void sendEmail(SendMessageForm messageForm, UUID userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);

        if (user.getAppPassword().isEmpty())
            throw new EntityNotContainAppPasswordException();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("host", user.getTypeSmtpEmail().getHost());
        jsonObject.put("user", user.getEmail());
        jsonObject.put("pass", user.getAppPassword());
        jsonObject.put("to", messageForm.getTo());
        jsonObject.put("subject", messageForm.getSubject());
        jsonObject.put("content", messageForm.getContent());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        restTemplate.postForEntity(urlReceiverService + "/api/v1/email/send", request, Void.class);
        return null;
    }


    private Map<String, String> mapFileFormToMap(List<FileForm> fileForm) {
        return fileForm.stream()
                .collect(Collectors.toMap(FileForm::getFileId, FileForm::getFilename));
    }
}
