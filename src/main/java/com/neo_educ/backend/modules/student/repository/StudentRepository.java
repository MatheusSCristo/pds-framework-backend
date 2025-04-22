package com.neo_educ.backend.modules.student.repository;

import com.neo_educ.backend.repository.AbstractRepository;
import com.neo_educ.backend.modules.student.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository extends AbstractRepository<StudentEntity> {
  Optional<StudentEntity> findByEmailAndTeacherEmail(String email, String teacherEmail);
  Optional<StudentEntity> findByIdAndTeacherEmail(Long id, String teacherEmail);
}

