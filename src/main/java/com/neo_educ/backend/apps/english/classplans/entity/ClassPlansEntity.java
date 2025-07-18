package com.neo_educ.backend.apps.english.classplans.entity;

import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.model.SessionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_plans")
public class ClassPlansEntity extends SessionEntity<TeacherEntity> {

    @NotBlank(message = "O tópico não pode estar em branco.")
    private String topic;

    @NotBlank(message = "O conteúdo não pode estar em branco.")
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    @Override
    public void setOwner(TeacherEntity owner) {
        super.setOwner(owner);
    }

    public ClassPlansEntity(String title, String topic, LocalDateTime date, TeacherEntity teacher, String generatedContent) {
        super.setTitle(title);
        super.setDate(date);
        super.setOwner(teacher);
        this.topic = topic;
        this.content = generatedContent;
    }
}