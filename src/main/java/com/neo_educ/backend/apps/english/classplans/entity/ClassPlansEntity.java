package com.neo_educ.backend.apps.english.classplans.entity;
import java.time.LocalDateTime;

import com.neo_educ.backend.apps.english.classplans.enums.ClassPlanStatus;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.AbstractPlanEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "class_plans")
@Entity(name = "class_plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClassPlansEntity extends AbstractPlanEntity {

  @ManyToOne
  @JoinColumn(name="teacher_id", nullable = false)
  private TeacherEntity teacher;
  private LocalDateTime classDate;
  @Enumerated(EnumType.STRING)
  private ClassPlanStatus status = ClassPlanStatus.PENDING;



}