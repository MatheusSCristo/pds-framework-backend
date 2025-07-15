package com.neo_educ.backend.apps.nutrition.nutritionist.repository;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;
import java.util.Optional;

public interface NutritionistRepository extends AbstractRepository<NutritionistEntity> {

    Optional<NutritionistEntity> findByEmail(String email);

    Optional<NutritionistEntity> findByInviteToken(String token);
}