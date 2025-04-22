package com.neo_educ.backend.modules.notes.controllers;

import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.useCase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/{studentId}/notes")
public class NotesController {

  @Autowired
  private CreateNoteUseCase createNoteUseCase;

  @Autowired
  private GetAllNotesUseCase getAllNotesUseCase;

  @Autowired
  private GetOneNoteInformationUseCase getOneNoteInformationUseCase;

  @Autowired
  private DeleteNoteUseCase deleteNoteUseCase;

  @Autowired
  private UpdateNoteInformationsUseCase updateNoteInformationsUseCase;

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

  @GetMapping("/")
  public ResponseEntity<Object> getAllNotes(@PathVariable Long studentId) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      List<NotesResponseDTO> result = getAllNotesUseCase.execute(teacherEmail, studentId);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<Object> getNote(@PathVariable Long studentId, @PathVariable Long noteId) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      NotesResponseDTO result = getOneNoteInformationUseCase.execute(teacherEmail, studentId, noteId);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{noteId}")
  public ResponseEntity<Object> delete(@PathVariable Long studentId, @PathVariable Long noteId) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      this.deleteNoteUseCase.execute(teacherEmail, studentId, noteId);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{noteId}")
  public ResponseEntity<Object> updateNoteInformations(@PathVariable Long noteId, @PathVariable Long studentId, @RequestBody NotesRequestDTO notesRequestDTO) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      var result = this.updateNoteInformationsUseCase.execute(teacherEmail, studentId, noteId, notesRequestDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
