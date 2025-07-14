package com.neo_educ.backend.apps.exercises.athleteActivity.service;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.athlete.repository.AthleteRepository;
import com.neo_educ.backend.apps.exercises.athleteActivity.dto.AthleteActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.athleteActivity.dto.AthleteActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.athleteActivity.entity.AthleteActivityEntity;
import com.neo_educ.backend.apps.exercises.athleteActivity.mapper.AthleteActivityMapper;
import com.neo_educ.backend.apps.exercises.athleteActivity.repository.AthleteActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AthleteActivityService {

    @Autowired
    private AthleteActivityRepository athleteActivityRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private AthleteActivityMapper athleteActivityMapper;

    public void createStudentActivity(AthleteActivityCreateDTO athleteActivityCreateDto) {
        AthleteEntity athleteEntity = athleteRepository.findById(athleteActivityCreateDto.athleteId())
                .orElseThrow(EntityNotFoundException::new);
        
        AthleteActivityEntity studentActivityEntity = athleteActivityMapper.toEntity(athleteActivityCreateDto);
        studentActivityEntity.setAthlete(athleteEntity);
        athleteActivityRepository.save(studentActivityEntity);
    }

    public List<AthleteActivityResponseDTO> getStudentActivities(Long studentId) {
        AthleteEntity athleteEntity = athleteRepository.findById(studentId)
                .orElseThrow(EntityNotFoundException::new);
        
        return athleteEntity.getActivities().stream().map(athleteActivityMapper::toDTO).toList();
    }
}