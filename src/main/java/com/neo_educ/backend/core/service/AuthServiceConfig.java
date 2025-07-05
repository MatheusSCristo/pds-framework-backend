package com.neo_educ.backend.core.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthServiceConfig {

    private final UserService personalService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceConfig(
            @Qualifier("personalService") UserService personalService,
            PasswordEncoder passwordEncoder) {
        this.personalService = personalService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthService authService() {
        return new AuthService(personalService, passwordEncoder);
    }

}