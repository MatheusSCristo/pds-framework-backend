package com.neo_educ.backend.apps.english.classplans.entity;

import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.model.Session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_plans")
public class ClassPlansEntity extends Session {

    private String topic;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;

    public ClassPlansEntity(String title, String s,LocalDateTime createdAt, TeacherEntity teacher, String generatedContent) {
        super.setTitle(title);
        this.setContent(generatedContent);
        this.teacher = (teacher);
        this.topic = (s);
        super.setDate(createdAt);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTopic(String topic) {}
}