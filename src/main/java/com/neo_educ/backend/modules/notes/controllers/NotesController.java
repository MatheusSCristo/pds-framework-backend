package com.neo_educ.backend.modules.notes.controllers;

import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<NotesResponseDTO> create(@PathVariable Long studentId, @RequestBody NotesRequestDTO notesRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherEmail = authentication.getName();

        NotesResponseDTO result = notesService.createNote(teacherEmail, studentId, notesRequestDTO);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Object> getAllNotes(@PathVariable Long studentId) {
        List<NotesResponseDTO> result = notesService.findAllNotes(studentId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Object> getNote(@PathVariable Long noteId) {
        NotesResponseDTO result = notesService.findNote(noteId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Object> delete(@PathVariable Long noteId) {
        notesService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }


}
