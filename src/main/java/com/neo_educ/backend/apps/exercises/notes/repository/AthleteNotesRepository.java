package com.neo_educ.backend.apps.exercises.notes.repository;

import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface AthleteNotesRepository extends AbstractRepository<AthleteNotesEntity> {
  Optional<AthleteNotesEntity> findByTitleAndAthleteId(String title, Long athleteId);
  List<AthleteNotesEntity> findAllByAthleteId(Long athleteId);
}
