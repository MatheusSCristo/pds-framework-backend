package com.neo_educ.backend.apps.english.notes.entity;

import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.AbstractNoteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "notes")
public class NotesEntity extends AbstractNoteEntity {

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private StudentEntity student;

}
