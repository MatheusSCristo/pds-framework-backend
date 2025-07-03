package com.neo_educ.backend.apps.english.student.repository;

import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends AbstractRepository<StudentEntity> {
  Optional<StudentEntity> findByEmailAndTeacher(String email, TeacherEntity teacher);

    StudentEntity findByTeacher(TeacherEntity teacher);

    List<StudentEntity> findAllByTeacher_Email(String email);

    List<StudentEntity> findAllByTeacherId(Long teacherId);
}

