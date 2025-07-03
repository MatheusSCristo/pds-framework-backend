package com.neo_educ.backend.apps.english.student.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.neo_educ.backend.apps.english.student.enums.InterestsEnum;
import com.neo_educ.backend.apps.english.student.enums.ProficiencyLevel;

public record StudentResponseDTO(Long id, String name, String email, ProficiencyLevel proficiencyLevel,
                                 List<InterestsEnum> interests,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt, String teacherEmail) {


}
