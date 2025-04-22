package com.neo_educ.backend.modules.notes.repository;

import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.Optional;

public interface NotesRepository extends AbstractRepository<NotesEntity> {
  Optional<NotesEntity> findByTitleAndTeacherEmailAndStudentId(String title, String teacherEmail, Long studentId);
  Optional<NotesEntity> findByIdAndTeacherEmailAndStudentId(Long id, String teacherEmail, Long studentId);
}
