package com.neo_educ.backend.apps.exercises.notes.repository;

import com.neo_educ.backend.apps.english.notes.entity.NotesEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends AbstractRepository<NotesEntity> {
  Optional<NotesEntity> findByTitleAndStudentId(String title, Long studentId);
  List<NotesEntity> findAllByStudentId(Long studentId);
}
