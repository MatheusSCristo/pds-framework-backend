package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

 public interface AuthServiceInterface {
    UserEntity signIn(LoginDTO input);
    UserEntity signUp(RegisterDTO input);
}

