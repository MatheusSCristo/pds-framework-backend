package com.neo_educ.backend.apps.exercises.athlete.repository;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.core.repository.AbstractClientRepository;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends AbstractClientRepository<AthleteEntity> {

    Optional<AthleteEntity> findByEmailAndOwner(String email, PersonalEntity owner);

    List<AthleteEntity> findAllByOwnerId(Long ownerId);
}