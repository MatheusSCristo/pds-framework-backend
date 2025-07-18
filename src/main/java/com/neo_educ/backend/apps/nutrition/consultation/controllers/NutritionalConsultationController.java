package com.neo_educ.backend.apps.nutrition.consultation.controllers;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationCreateDTO;
import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.core.controller.AbstractSessionController;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nutritional-consultations")
public class NutritionalConsultationController extends AbstractSessionController<
        NutritionalConsultationCreateDTO,
        NutritionalConsultationResponseDTO,
        NutritionistEntity
> {

    public NutritionalConsultationController(@Qualifier("nutritionFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}