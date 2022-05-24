package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken verifyExpiration(RefreshToken refreshTokenForm);

    RefreshToken createRefreshToken(UUID userId);

    Optional<RefreshToken> getRefreshTokenByToken(String token);
}
