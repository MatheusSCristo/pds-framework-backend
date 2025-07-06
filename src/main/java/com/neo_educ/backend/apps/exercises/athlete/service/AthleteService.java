package com.neo_educ.backend.apps.exercises.athlete.service;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.athlete.mapper.AthleteMapper;
import com.neo_educ.backend.apps.exercises.athlete.repository.AthleteRepository;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.repository.PersonalRepository;
import com.neo_educ.backend.core.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class AthleteService implements ClientService<AthleteEntity, AthleteRegisterDTO, AthleteResponseDTO> {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private AthleteMapper athleteMapper;

    @Autowired
    private PersonalRepository personalRepository;

    @Override
    @Transactional
    public AthleteResponseDTO create(AthleteRegisterDTO createDto, Long personalId) {
        PersonalEntity personal = personalRepository.findById(personalId)
                .orElseThrow(() -> new EntityNotFoundException("Personal not found with ID: " + personalId));

        if (athleteRepository.findByEmailAndPersonal(createDto.email(), personal).isPresent()) {
            throw new RuntimeException("Athlete já existente");
        }

        AthleteEntity entity = athleteMapper.toEntity(createDto);
        entity.setPersonal(personal);

        AthleteEntity savedAthlete = athleteRepository.save(entity);
        return athleteMapper.toResponseDTO(savedAthlete);
    }

    @Override
    public AthleteResponseDTO findById(Long id) {
        return athleteRepository.findById(id)
                .map(athleteMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Atleta com ID: " + id + " não encontrado"));
    }

    @Override
    public List<AthleteResponseDTO> findAll(Long personalId) {
        List<AthleteEntity> entities = athleteRepository.findAllByPersonalId(personalId);
        return entities.stream().map(athleteMapper::toResponseDTO).toList();
    }

    @Override
    public void delete(Long id) {
        if (!athleteRepository.existsById(id)) {
            throw new EntityNotFoundException("Atleta com ID: " + id + " não encontrado para deleção.");
        }
        athleteRepository.deleteById(id);
    }
}