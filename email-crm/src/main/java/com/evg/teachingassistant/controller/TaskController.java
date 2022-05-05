package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.tg.dto.FileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final RestTemplate restTemplate;

    private String token;

    public TaskController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/file/{fileId}")
    public void getPathFile(@PathVariable String fileId, HttpServletResponse httpServletResponse) throws IOException {
        String url = "https://api.telegram.org/";
        String urn = "bot" + token;
        FileDTO body = restTemplate.exchange(url + urn + "/getFile?file_id=" + fileId, HttpMethod.GET, null, new ParameterizedTypeReference<FileDTO>() {
        }).getBody();


        httpServletResponse.sendRedirect(url + "file/" + urn + "/" + body.getResult().getFile_path());
    }
}
