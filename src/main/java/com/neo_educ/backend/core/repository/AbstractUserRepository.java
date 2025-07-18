package com.neo_educ.backend.core.repository;

import com.neo_educ.backend.core.model.UserEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface AbstractUserRepository<T extends UserEntity> extends AbstractRepository<T> {


    Optional<T> findByEmail(String email);
  Optional<T> findByInviteToken(String token);
}