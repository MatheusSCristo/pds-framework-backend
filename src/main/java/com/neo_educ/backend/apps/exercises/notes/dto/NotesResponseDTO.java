package com.neo_educ.backend.apps.exercises.notes.dto;


import java.time.LocalDateTime;

public record NotesResponseDTO(Long id, String title, String content, LocalDateTime createdAt) {
}
