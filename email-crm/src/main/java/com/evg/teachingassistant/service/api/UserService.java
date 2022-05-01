package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.view.ProfileView;
import com.evg.teachingassistant.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    ProfileView getProfileByUserId(UUID userId);

    Optional<User> getUserById(UUID userId);

    User saveUser(SaveUserForm saveUserForm);
}
