package com.neo_educ.backend.modules.materialGeneration.dto;

import java.util.List;

public record GenerateMaterialDTO(String topic, String level, List<String> interests) {
}
