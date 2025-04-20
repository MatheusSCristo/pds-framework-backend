package com.neo_educ.backend.modules.notes.dto;

import com.neo_educ.backend.modules.notes.entity.NotesEntity;

import java.time.LocalDateTime;

public record NotesResponseDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String teacherEmail, Long studentId) {

  public static NotesResponseDTO fromEntity(NotesEntity notesEntity) {
    return new NotesResponseDTO(
        notesEntity.getId(),
        notesEntity.getTitle(),
        notesEntity.getContent(),
        notesEntity.getCreatedAt(),
        notesEntity.getUpdatedAt(),
        notesEntity.getTeacherEmail(),
        notesEntity.getStudentId()
    );
  }

}
