package com.evg.teachingassistant.controller;

import com.evg.teachingassistant.dto.form.RefreshTokenForm;
import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.form.UserAuthForm;
import com.evg.teachingassistant.dto.view.JwtView;
import com.evg.teachingassistant.dto.view.RefreshTokenView;
import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.service.api.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserView> registerUser(@RequestBody SaveUserForm saveUserForm) {
        return ResponseEntity.ok(authService.registerUser(saveUserForm));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtView> auth(@RequestBody UserAuthForm userAuthForm) {
        return ResponseEntity.ok(authService.auth(userAuthForm));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenView> refreshToken(@RequestBody RefreshTokenForm refreshTokenForm) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenForm));
    }
}
