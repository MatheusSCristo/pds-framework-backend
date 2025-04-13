package com.neo_educ.backend.modules.student.entity;

import com.neo_educ.backend.core.AbstractModel;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class StudentEntity extends AbstractModel {

  private String name;
  private String email;
  private String proficiencyLevel;

}
