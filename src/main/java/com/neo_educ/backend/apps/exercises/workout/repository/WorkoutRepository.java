package com.neo_educ.backend.apps.exercises.workout.repository;

import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends AbstractRepository<WorkoutEntity> {

  List<WorkoutEntity> findAllByPersonalId(Long teacherId);

  @Query("SELECT COUNT(cp) FROM WorkoutEntity cp WHERE cp.personal.id = :personalId AND cp.date BETWEEN :start AND :end")
  Long countConflictingPlans(
      @Param("personalId") Long personalId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end
  );
}
