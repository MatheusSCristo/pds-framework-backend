package com.neo_educ.backend.apps.nutrition.nutritionist.controller;

import com.neo_educ.backend.apps.nutrition.nutritionist.dto.NutritionistResponseDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.mappers.NutritionistMapper;
import com.neo_educ.backend.core.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nutritionists")
public class NutritionistController extends UserController<NutritionistEntity, NutritionistResponseDTO> {

    @Autowired
    private NutritionistMapper nutritionistMapper;

    @Override
    protected NutritionistResponseDTO mapToDto(NutritionistEntity entity) {
        return nutritionistMapper.toDTO(entity);
    }

}