package com.neo_educ.backend.model;

import java.time.LocalDateTime;

import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.enums.ClassPlanStatus;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;

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
public class ClassPlansEntity extends AbstractModel {

  @ManyToOne
  @JoinColumn(name="teacher_id", nullable = false)
  private TeacherEntity teacher;
  private String topic;
  private LocalDateTime classDate;

  @Column(columnDefinition = "TEXT")
  private String inputData;

  @Column(columnDefinition = "TEXT")
  private String aiGeneratedContent;

  @Enumerated(EnumType.STRING)
  private ClassPlanStatus status;
  
}