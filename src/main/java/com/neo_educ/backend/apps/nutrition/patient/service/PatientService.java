package com.neo_educ.backend.apps.nutrition.patient.service;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.patient.mapper.PatientMapper;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import com.neo_educ.backend.core.service.ClientService;
import com.neo_educ.backend.exceptions.patient.PatientAlreadyExistsException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService implements ClientService<PatientEntity, PatientRegisterDTO, PatientResponseDTO> {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Override
    @Transactional
    public PatientResponseDTO create(PatientRegisterDTO createDto, Long nutritionistId) {
        NutritionistEntity nutritionist = nutritionistRepository.findById(nutritionistId)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado com o ID: " + nutritionistId));

        if (patientRepository.findByEmailAndNutritionist(createDto.email(), nutritionist).isPresent()) {
            throw new PatientAlreadyExistsException();
        }

        PatientEntity entity = patientMapper.toEntity(createDto);
        entity.setNutritionist(nutritionist);

        PatientEntity savedPatient = patientRepository.save(entity);
        return patientMapper.toResponseDTO(savedPatient);
    }

    @Override
    public PatientResponseDTO findById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com ID: " + id + " não encontrado"));
    }

    @Override
    public List<PatientResponseDTO> findAll(Long nutritionistId) {
        List<PatientEntity> entities = patientRepository.findAllByNutritionistId(nutritionistId);
        return entities.stream().map(patientMapper::toResponseDTO).toList();
    }

    @Override
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente com ID: " + id + " não encontrado para deleção.");
        }
        patientRepository.deleteById(id);
    }
}