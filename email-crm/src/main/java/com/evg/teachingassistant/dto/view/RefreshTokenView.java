package com.evg.teachingassistant.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenView {
    private String token;
    private String requestRefreshToken;
}
