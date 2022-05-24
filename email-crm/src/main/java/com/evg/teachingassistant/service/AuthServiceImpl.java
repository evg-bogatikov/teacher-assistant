package com.evg.teachingassistant.service;

import com.evg.teachingassistant.dto.form.RefreshTokenForm;
import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.form.UserAuthForm;
import com.evg.teachingassistant.dto.view.JwtView;
import com.evg.teachingassistant.dto.view.RefreshTokenView;
import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.RefreshToken;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.security.jwt.JwtUtil;
import com.evg.teachingassistant.service.api.AuthService;
import com.evg.teachingassistant.service.api.RefreshTokenService;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JwtView auth(UserAuthForm userAuthForm) {
        User user = userService.getUserByEmailAndPassword(userAuthForm.getUsername(), userAuthForm.getPassword()).orElseThrow(EntityNotFoundException::new);
        String accessToken = jwtUtil.generateToken(userAuthForm.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new JwtView(accessToken, refreshToken.getToken(), user.getId(), user.getEmail(), user.getRoles());
    }

    @Override
    public UserView registerUser(SaveUserForm saveUserForm) {
        saveUserForm.setPassword(passwordEncoder.encode(saveUserForm.getPassword()));
        return userService.saveUser(saveUserForm);
    }

    @Override
    public RefreshTokenView refreshToken(RefreshTokenForm refreshTokenForm) {
        String requestRefreshToken = refreshTokenForm.getRefreshToken();

        return refreshTokenService.getRefreshTokenByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUsername).map(username -> {
            String token = jwtUtil.generateToken(username);
            return new RefreshTokenView(token, requestRefreshToken);
        }).orElseThrow(EntityNotFoundException::new);
    }
}
