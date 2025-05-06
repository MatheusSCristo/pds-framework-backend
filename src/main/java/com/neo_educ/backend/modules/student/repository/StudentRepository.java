package com.neo_educ.backend.modules.student.repository;

import com.neo_educ.backend.core.repository.AbstractRepository;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;

import java.util.Optional;

public interface StudentRepository extends AbstractRepository<StudentEntity> {
  Optional<StudentEntity> findByEmailAndTeacher(String email, TeacherEntity teacher);
}

