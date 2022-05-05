package com.evg.teachingassistant.service.api;

import com.evg.teachingassistant.dto.form.AddAppPasswordForm;
import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserView getUserViewById(UUID userId);

    UserView saveUser(SaveUserForm saveUserForm);
    UserView addAppPassword(AddAppPasswordForm addAppPasswordForm, UUID userId);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(UUID userId);

    Optional<User> getUserByEmailAndPassword(String username, String password);
}
