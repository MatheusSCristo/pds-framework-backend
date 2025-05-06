package com.neo_educ.backend.modules.student.dto;

import com.neo_educ.backend.modules.interests.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;

import java.util.List;

public record StudentRequestDTO(String name, String lastName, String email, String phone,
                                List<Integer> interests,
                                ProficiencyLevel proficiencyLevel,
                                String token) {
}
