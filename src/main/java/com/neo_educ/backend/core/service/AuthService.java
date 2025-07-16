package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.dto.auth.UserLoginDTO;
import com.neo_educ.backend.core.model.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

public class AuthService<T extends UserEntity> {

    private final UserService<T> userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService<T> userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signIn(UserLoginDTO infos) {
        return (UserEntity) userService.loadUserByUsername(infos.email());
    }

    public void signUp(T user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String token = UUID.randomUUID().toString().replace("-", "");
        user.setInviteToken(token);

        userService.signUp(user);
    }
}