package com.neo_educ.backend.modules.notes.controllers;

import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.useCase.CreateNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/{studentId}/notes")
public class NotesController {

  @Autowired
  private CreateNoteUseCase createNoteUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@PathVariable Long studentId, @RequestBody NotesRequestDTO notesRequestDTO) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      var result = createNoteUseCase.execute(teacherEmail, studentId, notesRequestDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
