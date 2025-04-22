package com.neo_educ.backend.modules.student.entity;

import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class StudentEntity extends AbstractModel {

  @NotBlank(message = "O campo [name] não pode ser vazio")
  private String name;

  @Email(message = "O campo [email] deve conter um email válido")
  private String email;

  @NotBlank(message = "O campo [proficiencyLevel] não pode ser vazio")
  private String proficiencyLevel;

  @ManyToOne
  @JoinColumn(name = "teacher_email", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
  private TeacherEntity teacher;

  @Column(name = "teacher_email", nullable = false)
  private String teacherEmail;

  @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
  private List<NotesEntity> notes;


}
