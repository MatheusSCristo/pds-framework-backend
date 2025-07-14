package com.neo_educ.backend.apps.exercises.athlete.dto;

import com.neo_educ.backend.apps.exercises.athlete.enums.InterestsEnum;
import com.neo_educ.backend.apps.exercises.athlete.enums.Sex;
import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AthleteResponseDTO(Long id, String name, String email, WorkoutLevel workoutLevel,
                                 Sex sex,
                                 Double weight,
                                 Double height,
                                 LocalDate dateOfBirth,
                                 List<InterestsEnum> interests,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt, String personalEmail) {


}
