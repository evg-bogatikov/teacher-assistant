package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User tempSaveUser(@RequestBody SaveUserForm saveUserForm){
        return userService.saveUser(saveUserForm);
    }
}
