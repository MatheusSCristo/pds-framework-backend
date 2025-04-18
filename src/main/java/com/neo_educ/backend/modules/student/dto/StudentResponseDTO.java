package com.neo_educ.backend.modules.student.dto;

import com.neo_educ.backend.modules.student.entity.StudentEntity;

import java.time.LocalDateTime;

public record StudentResponseDTO(Long id, String name, String email, String proficiencyLevel, LocalDateTime createdAt, LocalDateTime updatedAt) {

  public static StudentResponseDTO fromEntity(StudentEntity studentEntity) {
    return new StudentResponseDTO(
        studentEntity.getId(),
        studentEntity.getName(),
        studentEntity.getEmail(),
        studentEntity.getProficiencyLevel(),
        studentEntity.getCreatedAt(),
        studentEntity.getUpdatedAt()
    );
  }
}
