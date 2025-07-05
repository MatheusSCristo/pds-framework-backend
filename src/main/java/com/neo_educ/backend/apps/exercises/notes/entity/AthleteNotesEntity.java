package com.neo_educ.backend.apps.exercises.notes.entity;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.core.model.AbstractNoteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "athlete_notes")
public class AthleteNotesEntity extends AbstractNoteEntity {

  @ManyToOne
  @JoinColumn(name = "athlete_id", nullable = false)
  private AthleteEntity athlete;

}
