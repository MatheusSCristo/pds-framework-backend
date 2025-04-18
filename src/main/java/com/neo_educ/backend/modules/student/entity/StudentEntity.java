package com.neo_educ.backend.modules.student.entity;

import com.neo_educ.backend.model.AbstractModel;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
