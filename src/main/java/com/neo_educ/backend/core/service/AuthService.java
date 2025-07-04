package com.neo_educ.backend.core.service;

import com.neo_educ.backend.apps.english.auth.dto.LoginDTO;
import com.neo_educ.backend.core.model.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signIn(LoginDTO infos) {
        return (UserEntity) userService.loadUserByUsername(infos.email());
    }

    public UserEntity signUp(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userService.signUp(user);
    }
}
