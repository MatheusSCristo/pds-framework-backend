package com.neo_educ.backend.apps.english.materialGeneration.dto;

import java.util.List;

public record GenerateMaterialDTO(String topic, String level, List<String> interests) {
}
