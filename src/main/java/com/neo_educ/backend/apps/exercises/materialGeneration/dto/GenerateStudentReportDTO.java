package com.neo_educ.backend.apps.exercises.materialGeneration.dto;


import com.neo_educ.backend.apps.english.materialGeneration.dto.GradeAverageBySubject;

import java.util.List;

public record GenerateStudentReportDTO(
        List<GradeAverageBySubject> data
) {
}
