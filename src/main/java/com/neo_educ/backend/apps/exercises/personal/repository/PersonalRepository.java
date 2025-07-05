package com.neo_educ.backend.apps.exercises.personal.repository;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.Optional;

public interface PersonalRepository extends AbstractRepository<PersonalEntity> {

    Optional<PersonalEntity> findByEmail(String email);

    Optional<PersonalEntity> findByInviteToken(String token);


}
