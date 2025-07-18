package com.neo_educ.backend.apps.exercises.athlete.service;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.athlete.mapper.AthleteMapper;
import com.neo_educ.backend.apps.exercises.athlete.repository.AthleteRepository;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.repository.PersonalRepository;
import com.neo_educ.backend.core.mapper.ClientMapper;
import com.neo_educ.backend.core.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AthleteService implements ClientService<AthleteEntity, AthleteRegisterDTO, AthleteResponseDTO, PersonalEntity> {

    private final AthleteRepository athleteRepository;
    private final AthleteMapper athleteMapper;
    private final PersonalRepository personalRepository;

    @Override
    public AthleteRepository getRepository() {
        return this.athleteRepository;
    }

    @Override
    public ClientMapper<AthleteRegisterDTO, AthleteResponseDTO, AthleteEntity> getModelMapper() {
        return this.athleteMapper;
    }

    @Override
    public JpaRepository<PersonalEntity, Long> getOwnerRepository() {
        return this.personalRepository;
    }


    @Override
    @Transactional
    public AthleteResponseDTO create(AthleteRegisterDTO createDto, Long ownerId) {
        PersonalEntity personal = personalRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Personal não encontrado com ID: " + ownerId));

        athleteRepository.findByEmailAndOwner(createDto.email(), personal).ifPresent(athlete -> {
            throw new RuntimeException("Atleta já existente para este personal.");
        });

        AthleteEntity entity = athleteMapper.toEntity(createDto);
        entity.setOwner(personal);

        AthleteEntity savedAthlete = athleteRepository.save(entity);
        return athleteMapper.toResponse(savedAthlete);
    }
    @Override
    public List<AthleteResponseDTO> findAll(Long ownerId) {
        List<AthleteEntity> entities = athleteRepository.findAllByOwnerId(ownerId);
        return entities.stream().map(athleteMapper::toResponse).toList();
    }

}