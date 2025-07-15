package com.neo_educ.backend.apps.nutrition.consultation.service;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationCreateDTO;
import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import com.neo_educ.backend.apps.nutrition.consultation.mappers.NutritionalConsultationMapper;
import com.neo_educ.backend.apps.nutrition.consultation.repository.NutritionalConsultationRepository;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.exceptions.ConflictException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NutritionalConsultationService implements SessionService<NutritionalConsultationEntity, NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO> {

    @Autowired
    private NutritionalConsultationRepository nutritionalConsultationRepository;

    @Autowired
    private NutritionistRepository nutritionistRepository; // Repositório do nutricionista

    @Autowired
    private NutritionalConsultationMapper nutritionalConsultationMapper;

    @Autowired
    @Qualifier("nutritionActivityService") // Gerador de atividade de nutrição
    private ActivityGeneratorService activityGenerator;

    @Override
    public NutritionalConsultationResponseDTO create(NutritionalConsultationCreateDTO data, Long nutritionistID) {
        // 1. Busca o nutricionista ou lança uma exceção
        NutritionistEntity nutritionist = nutritionistRepository.findById(nutritionistID)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado"));

        // 2. Verifica se há conflito de horários
        LocalDateTime consultationDate = data.consultationDate();
        LocalDateTime start = consultationDate.minusMinutes(30);
        LocalDateTime end = consultationDate.plusMinutes(30);

        Long conflicts = nutritionalConsultationRepository.countConflictingConsultations(nutritionistID, start, end);

        if (conflicts > 0) {
            throw new ConflictException("Já existe uma consulta marcada nesse intervalo de 30 minutos.");
        }
    

        // 4. Cria e salva a nova entidade de consulta
        NutritionalConsultationEntity entity = new NutritionalConsultationEntity(
                data.title(),
                data.consultationType(),
                data.consultationDate(),
                nutritionist
        );
        
        NutritionalConsultationEntity consultation = nutritionalConsultationRepository.save(entity);
        return nutritionalConsultationMapper.toResponse(consultation);
    }

    @Override
    public NutritionalConsultationResponseDTO findByID(Long id) {
        Optional<NutritionalConsultationEntity> optionalConsultation = nutritionalConsultationRepository.findById(id);

        if (optionalConsultation.isEmpty()) {
            throw new EntityNotFoundException("Consulta não encontrada com o ID: " + id);
        }

        return nutritionalConsultationMapper.toResponse(optionalConsultation.get());
    }

    @Override
    public List<NutritionalConsultationResponseDTO> findAll(Long nutritionistID) {
        List<NutritionalConsultationEntity> consultations = nutritionalConsultationRepository.findAllByNutritionist_Id(nutritionistID);
        return consultations.stream().map(nutritionalConsultationMapper::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        this.nutritionalConsultationRepository.deleteById(id);
    }

}