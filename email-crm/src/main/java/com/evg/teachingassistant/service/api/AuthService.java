package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.dto.form.RefreshTokenForm;
import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.form.UserAuthForm;
import com.evg.teachingassistant.dto.view.JwtView;
import com.evg.teachingassistant.dto.view.RefreshTokenView;
import com.evg.teachingassistant.dto.view.UserView;

public interface AuthService {
    JwtView auth(UserAuthForm userAuthForm);

    UserView registerUser(SaveUserForm saveUserForm);

    RefreshTokenView refreshToken(RefreshTokenForm refreshTokenForm);
}
