package com.neo_educ.backend.apps.exercises.workout.repository;

import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends AbstractRepository<WorkoutEntity> {

    List<WorkoutEntity> findAllByOwnerId(Long ownerId);

    @Query("SELECT COUNT(w) FROM WorkoutEntity w WHERE w.owner.id = :ownerId AND w.date BETWEEN :start AND :end")
    Long countConflictingPlans(
            @Param("ownerId") Long ownerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}