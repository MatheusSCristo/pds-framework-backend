package com.neo_educ.backend.apps.exercises.materialGeneration.dto;


import java.util.List;

public record GenerateAthleteReportDTO(
        List<MetricsAverageBySubject> data
) {
}
