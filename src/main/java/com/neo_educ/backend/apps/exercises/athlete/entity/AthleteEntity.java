package com.neo_educ.backend.apps.exercises.athlete.entity;

import com.neo_educ.backend.apps.exercises.athlete.enums.InterestsEnum;
import com.neo_educ.backend.apps.exercises.athlete.enums.Sex;
import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;
import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.athleteActivity.entity.AthleteActivityEntity;
import com.neo_educ.backend.core.model.ClientEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "athlete")
public class AthleteEntity extends ClientEntity {
    private Double height;
    private Double weight;
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    private Sex sex;
    @NotNull
    @ElementCollection(targetClass = InterestsEnum.class)
    @CollectionTable(name = "athlete_interests", joinColumns = @JoinColumn(name = "athlete_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest")
    private List<InterestsEnum> interests;

    private WorkoutLevel workoutLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonalEntity personal;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private List<AthleteNotesEntity> notes;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private List<AthleteActivityEntity> activities;

}
