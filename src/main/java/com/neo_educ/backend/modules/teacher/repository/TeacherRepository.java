package com.neo_educ.backend.modules.teacher.repository;

import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.Optional;

public interface TeacherRepository extends AbstractRepository<TeacherEntity> {

    Optional<TeacherEntity> findByEmail(String email);

}
