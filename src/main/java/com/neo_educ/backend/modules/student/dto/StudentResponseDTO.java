package com.neo_educ.backend.modules.student.dto;

import com.neo_educ.backend.modules.interests.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;

import java.time.LocalDateTime;
import java.util.List;

public record StudentResponseDTO(Long id, String name, String email, ProficiencyLevel proficiencyLevel,
                                 List<InterestsEnum> interests,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt, String teacherEmail) {


}
