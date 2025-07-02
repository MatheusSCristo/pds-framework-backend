package com.neo_educ.backend.core.service;

import com.neo_educ.backend.modules.materialGeneration.dto.GenerateExerciseDTO;

import java.util.Map;

public interface ActivityGeneratorService {

    String generateActivity(Map<String, Object> infos);

}
