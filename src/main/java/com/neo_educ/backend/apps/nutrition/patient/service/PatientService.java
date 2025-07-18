package com.neo_educ.backend.apps.nutrition.patient.service;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.patient.mapper.PatientMapper;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import com.neo_educ.backend.core.mapper.ClientMapper;
import com.neo_educ.backend.core.service.ClientService;
import com.neo_educ.backend.exceptions.patient.PatientAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService implements ClientService<PatientEntity, PatientRegisterDTO, PatientResponseDTO, NutritionistEntity> {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final NutritionistRepository nutritionistRepository;

    @Override
    public PatientRepository getRepository() {
        return this.patientRepository;
    }

    @Override
    public JpaRepository<NutritionistEntity, Long> getOwnerRepository() {
        return this.nutritionistRepository;
    }

    @Override
    public ClientMapper<PatientRegisterDTO, PatientResponseDTO, PatientEntity> getModelMapper() {
        return this.patientMapper;
    }
    
    @Override
    public List<PatientResponseDTO> findAll(Long ownerId) {
        return patientRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(patientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientResponseDTO create(PatientRegisterDTO createDto, Long ownerId) {
        NutritionistEntity nutritionist = nutritionistRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado com o ID: " + ownerId));

        patientRepository.findByEmailAndOwner(createDto.email(), nutritionist).ifPresent(patient -> {
            throw new PatientAlreadyExistsException();
        });

        /**
         * ✅ A CORREÇÃO DE LÓGICA: Usamos o builder para criar a entidade,
         * garantindo explicitamente que o 'owner' está sendo definido.
         */
        PatientEntity entity = PatientEntity.builder()
                .name(createDto.name())
                .lastName(createDto.lastName())
                .email(createDto.email())
                .phone(createDto.phone())
                .allergies(createDto.allergies())
                .nutritionalGoals(createDto.nutritionalGoals())
                .owner(nutritionist)
                .build();

        // 4. Salva a entidade e retorna a resposta
        PatientEntity savedPatient = patientRepository.save(entity);
        return patientMapper.toResponse(savedPatient);
     }

}