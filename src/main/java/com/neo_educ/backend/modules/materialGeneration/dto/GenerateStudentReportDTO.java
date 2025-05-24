package com.neo_educ.backend.modules.materialGeneration.dto;


import java.util.List;

public record GenerateStudentReportDTO(
        List<GradeAverageBySubject> data
) {
}
