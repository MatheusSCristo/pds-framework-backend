package com.neo_educ.backend.core.service;

import com.neo_educ.backend.apps.english.auth.dto.LoginDTO;
import com.neo_educ.backend.core.model.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço de autenticação genérico do framework.
 *
 * A lógica de negócio específica de cada tipo de usuário é delegada para a
 * implementação de UserService fornecida pela ApplicationFactory.
 */
@Service
public class AuthService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    // A instância de UserService específica (ex: EnglishTeacherUserService)
    // será injetada pela fábrica no momento da criação.
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Realiza o processo de login.
     *
     * @param infos DTO com as credenciais de login.
     * @return A entidade do usuário autenticado.
     */
    public UserEntity signIn(LoginDTO infos) {
        return (UserEntity) userService.loadUserByUsername(infos.email());
    }

    public UserEntity signUp(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userService.signUp(user);
    }
}
