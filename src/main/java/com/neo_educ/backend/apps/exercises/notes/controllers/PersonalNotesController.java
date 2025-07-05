package com.neo_educ.backend.apps.exercises.notes.controllers;

import com.neo_educ.backend.apps.exercises.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.exercises.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.exercises.notes.service.PersonalNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personal/notes/")
public class PersonalNotesController {

    @Autowired
    private PersonalNotesService notesService;

    @PostMapping("/{athleteId}")
    public ResponseEntity<NotesResponseDTO> create(@PathVariable Long athleteId, @RequestBody NotesRequestDTO notesRequestDTO) {
        NotesResponseDTO result = notesService.createNote( athleteId, notesRequestDTO);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{athleteId}")
    public ResponseEntity<Object> getAllNotes(@PathVariable Long athleteId) {
        List<NotesResponseDTO> result = notesService.findAllNotes(athleteId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Object> delete(@PathVariable Long noteId) {
        notesService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }


}
