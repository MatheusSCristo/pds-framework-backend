package com.neo_educ.backend.core.service;


import com.neo_educ.backend.core.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface de serviço genérica para operações relacionadas a usuários.
 *
 * Cada módulo de aplicação deverá fornecer uma implementação que opere
 * sobre sua entidade de usuário concreta (Professor, Nutricionista, etc.).
 */
public interface UserService extends UserDetailsService {

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param user A entidade de usuário a ser salva.
     * @return A entidade de usuário salva.
     */
    UserEntity signUp(UserEntity user);

}
