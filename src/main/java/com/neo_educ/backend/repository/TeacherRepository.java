package com.neo_educ.backend.repository;

import com.neo_educ.backend.model.TeacherEntity;

import java.util.Optional;

public interface TeacherRepository extends AbstractRepository<TeacherEntity> {

    Optional<TeacherEntity> findByEmail(String email);

}
