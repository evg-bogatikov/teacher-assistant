package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.dto.form.AddAppPasswordForm;
import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.security.CustomUserDetails;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add/appPassword")
    public ResponseEntity<UserView> addAppPassword(@Valid @RequestBody AddAppPasswordForm addAppPasswordForm) {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(userService.addAppPassword(addAppPasswordForm, userId));
    }

    @GetMapping("/")
    public ResponseEntity<UserView> getUser() {
        UUID userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(userService.getUserViewById(userId));
    }
}
