package com.neo_educ.backend.apps.nutrition.patient.repository;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends AbstractRepository<PatientEntity> {

    Optional<PatientEntity> findByEmailAndNutritionist(String email, NutritionistEntity nutritionist);

    List<PatientEntity> findAllByNutritionist_Email(String email);

    List<PatientEntity> findAllByNutritionistId(Long nutritionistId);
    
}