package com.neo_educ.backend.apps.exercises.athlete.entity;

import com.neo_educ.backend.apps.exercises.athlete.enums.InterestsEnum;
import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;
import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.studentActivity.entity.AthleteActivityEntity;
import com.neo_educ.backend.core.model.ClientEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class AthleteEntity extends ClientEntity {

    @NotNull
    @ElementCollection(targetClass = InterestsEnum.class)
    @CollectionTable(name = "interests", joinColumns = @JoinColumn(name = "student_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest")
    private List<InterestsEnum> interests;

    private WorkoutLevel workoutLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonalEntity personal;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.REMOVE)
    private List<AthleteNotesEntity> notes;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<AthleteActivityEntity> activities;

}
