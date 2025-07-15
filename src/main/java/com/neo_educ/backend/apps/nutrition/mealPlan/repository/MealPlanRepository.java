package com.neo_educ.backend.apps.nutrition.mealPlan.repository;

import com.neo_educ.backend.apps.nutrition.mealPlan.entity.MealPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlanEntity, Long> {
    // Busca todos os planos alimentares de um paciente específico pelo ID do paciente
    List<MealPlanEntity> findAllByPatientId(Long patientId);
}