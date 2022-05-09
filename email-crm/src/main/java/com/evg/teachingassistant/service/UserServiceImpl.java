package com.evg.teachingassistant.service;

import com.evg.teachingassistant.dto.form.AddAppPasswordForm;
import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.view.UserView;
import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.exception.EntityNotMatchRoleException;
import com.evg.teachingassistant.exception.EntityNotSaveException;
import com.evg.teachingassistant.model.user.Role;
import com.evg.teachingassistant.model.user.TypeImapEmail;
import com.evg.teachingassistant.model.user.TypeSmtpEmail;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.repository.UserRepository;
import com.evg.teachingassistant.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserView getUserViewById(UUID userId) {
        User byId = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return mapUserToUserView(byId);
    }

    @Override
    public UserView saveUser(SaveUserForm saveUserForm) {
        if (userRepository.existsByEmail(saveUserForm.getEmail())) {
            throw new EntityNotSaveException();
        }
        User savedUser = userRepository.save(new User(
                UUID.randomUUID(),
                saveUserForm.getFirstName(),
                saveUserForm.getLastName(),
                saveUserForm.getPassword(),
                saveUserForm.getEmail(),
                getTypeImapEmailFromEmail(saveUserForm.getEmail()),
                getTypeSmtpEmailFromEmail(saveUserForm.getEmail()),
                "",
                Set.of(Role.ROLE_USER)
        ));
        return mapUserToUserView(savedUser);
    }

    @Override
    public UserView addAppPassword(AddAppPasswordForm addAppPasswordForm, UUID userId) {
        User user = userRepository.findById(UUID.fromString(addAppPasswordForm.getUserId()))
                .orElseThrow(EntityNotFoundException::new);
        if (!user.getId().equals(userId)) {
            throw new EntityNotMatchRoleException();
        }

        user.setAppPassword(addAppPasswordForm.getAppPassword());
        User savedUser = userRepository.save(user);
        return mapUserToUserView(savedUser);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String username, String password) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(EntityNotFoundException::new);
        if (BCrypt.checkpw(password, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    private TypeImapEmail getTypeImapEmailFromEmail(String email) {
        String s = email.split("@")[1];
        String s1 = s.split("\\.")[0];
        return TypeImapEmail.valueOf(s1.toUpperCase(Locale.ROOT));
    }

    private TypeSmtpEmail getTypeSmtpEmailFromEmail(String email) {
        String s = email.split("@")[1];
        String s1 = s.split("\\.")[0];
        return TypeSmtpEmail.valueOf(s1.toUpperCase(Locale.ROOT));
    }

    private UserView mapUserToUserView(User user) {
        return new UserView(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}
