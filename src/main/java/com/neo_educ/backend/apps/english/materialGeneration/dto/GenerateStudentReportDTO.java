package com.neo_educ.backend.apps.english.materialGeneration.dto;


import java.util.List;

public record GenerateStudentReportDTO(
        List<GradeAverageBySubject> data
) {
}
