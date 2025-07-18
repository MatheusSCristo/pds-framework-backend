package com.neo_educ.backend.apps.nutrition.patient.repository;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.repository.AbstractClientRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends AbstractClientRepository<PatientEntity> {

    Optional<PatientEntity> findByEmailAndOwner(String email, NutritionistEntity owner);

    List<PatientEntity> findAllByOwnerId(Long ownerId);
}