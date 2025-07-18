package com.neo_educ.backend.apps.exercises.workout.entity;

import com.neo_educ.backend.apps.exercises.workout.enums.WorkoutGoal;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.core.model.SessionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workout")
public class WorkoutEntity extends SessionEntity<PersonalEntity> {

    @NotBlank(message = "O tópico não pode estar em branco.")
    private String topic;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull(message = "O objetivo do treino é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutGoal goal;

    @NotBlank(message = "A duração deve ser especificada.")
    private String duration;

    @NotNull(message = "O número de treinos por semana é obrigatório.")
    private Integer workoutsPerWeek;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id", nullable = false)
    @Override
    public void setOwner(PersonalEntity owner) {
        super.setOwner(owner);
    }
    
}