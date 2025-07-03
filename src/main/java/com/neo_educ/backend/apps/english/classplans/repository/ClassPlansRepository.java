package com.neo_educ.backend.apps.english.classplans.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

public interface ClassPlansRepository extends AbstractRepository<ClassPlansEntity> {

  List<ClassPlansEntity> findAllByTeacher_Id(Long teacherId);

  @Query("SELECT COUNT(cp) FROM class_plan cp WHERE cp.teacher.id = :teacherId AND cp.classDate BETWEEN :start AND :end")
  Long countConflictingPlans(
      @Param("teacherId") Long teacherId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end
  );
}
