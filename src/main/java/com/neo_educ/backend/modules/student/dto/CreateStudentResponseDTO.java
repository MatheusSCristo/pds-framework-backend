package com.neo_educ.backend.modules.student.dto;

import com.neo_educ.backend.modules.student.entity.StudentEntity;

public record CreateStudentResponseDTO(Long id, String name, String email, String proficiencyLevel) {

  public static CreateStudentResponseDTO fromEntity(StudentEntity studentEntity) {
    return new CreateStudentResponseDTO(
        studentEntity.getId(),
        studentEntity.getName(),
        studentEntity.getEmail(),
        studentEntity.getProficiencyLevel()
    );
  }
}
