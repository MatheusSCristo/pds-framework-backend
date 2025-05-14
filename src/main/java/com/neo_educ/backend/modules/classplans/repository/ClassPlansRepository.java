package com.neo_educ.backend.modules.classplans.repository;

import java.util.List;

import com.neo_educ.backend.core.repository.AbstractRepository;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;

public interface ClassPlansRepository extends AbstractRepository<ClassPlansEntity> {
  
  List<ClassPlansEntity> findAllByTeacher_Id(Long teacherId);

}
