package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.repository.AbstractUserRepository;
import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<T extends UserEntity> extends UserDetailsService {

    default T signUp(T user) {
        getRepository().findByEmail(user.getEmail()).ifPresent(u -> {
            throw getAlreadyExistsException();
        });

        return getRepository().save(user);
    }

    AbstractUserRepository<T> getRepository();

    ApiException getAlreadyExistsException();
}