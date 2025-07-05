package com.neo_educ.backend.apps.exercises.workout.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neo_educ.backend.core.repository.AbstractRepository;

public interface ClassPlansRepository extends AbstractRepository<com.neo_educ.backend.apps.exercises.workout.entity.ClassPlansEntity> {

  List<com.neo_educ.backend.apps.exercises.workout.entity.ClassPlansEntity> findAllByTeacher_Id(Long teacherId);

  @Query("SELECT COUNT(cp) FROM ClassPlansEntity cp WHERE cp.teacher.id = :teacherId AND cp.date BETWEEN :start AND :end")
  Long countConflictingPlans(
      @Param("teacherId") Long teacherId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end
  );
}
