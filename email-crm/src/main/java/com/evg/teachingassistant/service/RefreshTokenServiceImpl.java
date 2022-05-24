package com.evg.teachingassistant.service;

import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.RefreshToken;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.repository.RefreshTokenRepository;
import com.evg.teachingassistant.service.api.RefreshTokenService;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refreshTokenExpirationMs}")
    private Long refreshTokenDuration;

    public RefreshTokenServiceImpl(UserService userService, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            return null;
        }
        return refreshToken;
    }

    @Override
    public RefreshToken createRefreshToken(UUID userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID(),
                user.getEmail(),
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(refreshTokenDuration)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> getRefreshTokenByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
