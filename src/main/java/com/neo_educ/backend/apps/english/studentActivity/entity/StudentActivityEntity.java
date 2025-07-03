package com.neo_educ.backend.apps.english.studentActivity.entity;

import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.studentActivity.enums.StudentActivityStatus;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.ClientProgressMonitoringEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "student_activity")
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentActivityEntity extends ClientProgressMonitoringEntity {
    private Integer unit;
    private Integer grade;
    private StudentActivityStatus status;
    private String subject;
    @ManyToOne
    private StudentEntity student;


}
