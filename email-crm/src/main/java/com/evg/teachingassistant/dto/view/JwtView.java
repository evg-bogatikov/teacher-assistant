package com.evg.teachingassistant.dto.view;

import com.evg.teachingassistant.model.user.Role;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class JwtView {
    @NonNull
    private String accessToken;
    @NonNull
    private String refreshToken;
    @NonNull
    private UUID userId;
    @NonNull
    private String email;
    @NonNull
    private Set<Role> roleSet;
    private String tokenType = "Bearer";
}
