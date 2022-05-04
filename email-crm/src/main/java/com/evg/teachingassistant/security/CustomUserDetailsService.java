package com.evg.teachingassistant.security;

import com.evg.teachingassistant.exception.EntityNotFoundException;
import com.evg.teachingassistant.model.user.User;
import com.evg.teachingassistant.service.api.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username)
                .orElseThrow(EntityNotFoundException::new);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
