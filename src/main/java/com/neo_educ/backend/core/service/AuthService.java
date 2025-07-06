package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.dto.auth.UserLoginDTO;
import com.neo_educ.backend.core.model.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signIn(UserLoginDTO infos) {
        return (UserEntity) userService.loadUserByUsername(infos.email());
    }

    public void signUp(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.signUp(user);
    }
}
