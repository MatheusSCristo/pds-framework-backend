package com.neo_educ.backend.modules.notes.repository;

import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.repository.AbstractRepository;

import java.util.Optional;

public interface NotesRepository extends AbstractRepository<NotesEntity> {
  Optional<NotesEntity> findByTitleAndTeacherEmailAndStudentId(String title, String teacherEmail, Long studentId);
}
