package com.neo_educ.backend.apps.exercises.workout.entity;

import com.neo_educ.backend.apps.exercises.workout.enums.WorkoutGoal;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.core.model.Session;
import jakarta.persistence.*;
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
@Table(name = "workout_plan")
public class WorkoutPlanEntity extends Session {

    private String topic;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutGoal goal;

    private String duration;

    private Integer workoutsPerWeek;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "personal_id", nullable = false)
    private PersonalEntity personal;

    public WorkoutPlanEntity(String title, String s,LocalDateTime createdAt, PersonalEntity personal, String generatedContent) {
        super.setTitle(title);
        this.setContent(generatedContent);
        this.personal = personal;
        this.topic = s;
        super.setDate(createdAt);
    }

}