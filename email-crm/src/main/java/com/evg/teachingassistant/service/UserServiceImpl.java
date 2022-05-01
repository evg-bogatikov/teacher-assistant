package com.evg.teachingassistant.service;

import com.evg.teachingassistant.dto.form.SaveUserForm;
import com.evg.teachingassistant.dto.view.ProfileView;
import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.user.Role;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.repository.UserRepository;
import com.evg.teachingassistant.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public ProfileView getProfileByUserId(UUID userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            return new ProfileView(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getTypeEmail()
            );
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User saveUser(SaveUserForm saveUserForm) {
        User entity = new User(
                UUID.randomUUID(),
                saveUserForm.getName(),
                saveUserForm.getPassword(),
                saveUserForm.getEmail(),
                saveUserForm.getTypeEmail(),
                saveUserForm.getAppPassword(),
                List.of(Role.ROLE_USER)
        );
        logger.info("User id: {}", entity.getId());
        return userRepository.save(entity);
    }
}
