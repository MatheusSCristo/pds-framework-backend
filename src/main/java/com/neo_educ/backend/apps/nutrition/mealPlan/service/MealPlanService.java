package com.neo_educ.backend.apps.nutrition.mealPlan.service;

import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GenerateMealPlanDTO;
import com.neo_educ.backend.apps.nutrition.mealPlan.dto.MealPlanResponseDTO;
import com.neo_educ.backend.apps.nutrition.mealPlan.entity.MealPlanEntity;
import com.neo_educ.backend.apps.nutrition.mealPlan.mapper.MealPlanMapper;
import com.neo_educ.backend.apps.nutrition.mealPlan.repository.MealPlanRepository;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MealPlanMapper mealPlanMapper;

    @Autowired
    @Qualifier("nutritionActivityService")
    private ActivityGeneratorService activityGenerator;

    @Transactional
    public MealPlanResponseDTO createMealPlan(Long patientId, String category) {
        PatientEntity patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + patientId + " não encontrado."));

        GenerateMealPlanDTO generatorDTO = new GenerateMealPlanDTO(patientId, category);


        String generatedContent = activityGenerator.generateActivityContent(generatorDTO);

        MealPlanEntity newMealPlan = MealPlanEntity.builder()
                .title(category) 
                .content(generatedContent)
                .patient(patient)
                .startDate(LocalDate.now()) 
                .endDate(LocalDate.now().plusDays(7)) 
                .build();

        MealPlanEntity savedPlan = mealPlanRepository.save(newMealPlan);
        return mealPlanMapper.toResponseDTO(savedPlan);
    }

    public MealPlanResponseDTO findPlanById(Long id) {
        return mealPlanRepository.findById(id)
                .map(mealPlanMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Plano alimentar com ID " + id + " não encontrado."));
    }

    public List<MealPlanResponseDTO> findAllPlansByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Paciente com ID " + patientId + " não encontrado.");
        }
        List<MealPlanEntity> plans = mealPlanRepository.findAllByPatientId(patientId);
        return plans.stream().map(mealPlanMapper::toResponseDTO).toList();
    }

    public void deletePlan(Long id) {
        if (!mealPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("Plano alimentar com ID " + id + " não encontrado para deleção.");
        }
        mealPlanRepository.deleteById(id);
    }
}