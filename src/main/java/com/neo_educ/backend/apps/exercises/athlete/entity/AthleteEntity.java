package com.neo_educ.backend.apps.exercises.athlete.entity;

import com.neo_educ.backend.apps.exercises.athlete.enums.InterestsEnum;
import com.neo_educ.backend.apps.exercises.athlete.enums.Sex;
import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;
import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.athleteActivity.entity.AthleteActivityEntity;
import com.neo_educ.backend.core.model.ClientEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "athlete")
public class AthleteEntity extends ClientEntity<PersonalEntity> {

    @NotNull(message = "A altura é obrigatória.")
    private Double height;

    @NotNull(message = "O peso é obrigatório.")
    private Double weight;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "O sexo é obrigatório.")
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @NotEmpty(message = "Pelo menos um interesse deve ser selecionado.")
    @ElementCollection(targetClass = InterestsEnum.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "athlete_interests", joinColumns = @JoinColumn(name = "athlete_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest", nullable = false)
    private List<InterestsEnum> interests;

    @NotNull(message = "O nível de treino é obrigatório.")
    @Enumerated(EnumType.ORDINAL)
    private WorkoutLevel workoutLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id", nullable = false)
    @Override
    public void setOwner(PersonalEntity user) {
        super.setOwner(user);
    }

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AthleteNotesEntity> notes = new ArrayList<>();

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AthleteActivityEntity> activities = new ArrayList<>();
}