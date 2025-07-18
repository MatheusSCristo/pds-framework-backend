package com.neo_educ.backend.apps.nutrition.consultation.service;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationCreateDTO;
import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import com.neo_educ.backend.apps.nutrition.consultation.mappers.NutritionalConsultationMapper;
import com.neo_educ.backend.apps.nutrition.consultation.repository.NutritionalConsultationRepository;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.core.mapper.SessionMapper;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.exceptions.ConflictException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NutritionalConsultationService implements SessionService<NutritionalConsultationEntity, NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO, NutritionistEntity> {

    private final NutritionalConsultationRepository consultationRepository;
    private final NutritionistRepository nutritionistRepository;
    private final NutritionalConsultationMapper consultationMapper;


    @Override
    public JpaRepository<NutritionalConsultationEntity, Long> getRepository() {
        return this.consultationRepository;
    }

    @Override
    public SessionMapper<NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO, NutritionalConsultationEntity> getMapper() {
        return this.consultationMapper;
    }

    @Override
    public JpaRepository<NutritionistEntity, Long> getOwnerRepository() {
        return this.nutritionistRepository;
    }
    
    @Override
    public List<NutritionalConsultationResponseDTO> findAll(Long ownerId) {
        return consultationRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(consultationMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public NutritionalConsultationResponseDTO create(NutritionalConsultationCreateDTO data, Long ownerId) {
        NutritionistEntity nutritionist = nutritionistRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado"));

        LocalDateTime consultationDate = data.date();
        LocalDateTime start = consultationDate.minusMinutes(30);
        LocalDateTime end = consultationDate.plusMinutes(30);

        if (consultationRepository.countConflictingConsultations(ownerId, start, end) > 0) {
            throw new ConflictException("Já existe uma consulta marcada nesse intervalo de 30 minutos.");
        }

        NutritionalConsultationEntity entity = NutritionalConsultationEntity.builder()
                .title(data.title())
                .date(data.date())
                .owner(nutritionist)
                .consultationType(data.consultationType())
                .build();
        
        NutritionalConsultationEntity consultation = consultationRepository.save(entity);
        return consultationMapper.toResponse(consultation);
    }

}