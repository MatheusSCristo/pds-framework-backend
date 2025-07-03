// src/main/java/com/neo_educ/backend/modules/materialGeneration/service/MaterialGeneratorService.java
package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import com.neo_educ.backend.exceptions.generateMaterial.LevelNullException;
import com.neo_educ.backend.exceptions.generateMaterial.TopicNullException;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.modules.student.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
// IMPORTANT: Esta classe NÃO estende MaterialGeneratorServiceAbstract.
// Ela é a fachada/dispatcher para o gerador de materiais (atualmente apenas o de inglês).
public class MaterialGeneratorService { // Renomeado de GenerateMaterialService para consistência

    private final EnglishMaterialGeneratorService englishMaterialGeneratorService; // Injeta a implementação específica
    private final StudentService studentService;

    @Autowired
    public MaterialGeneratorService(EnglishMaterialGeneratorService englishMaterialGeneratorService, StudentService studentService) {
        this.englishMaterialGeneratorService = englishMaterialGeneratorService;
        this.studentService = studentService;
    }

    public String generateMaterial(GenerateMaterialDTO generateMaterialDTO) {
        if (generateMaterialDTO.topic() == null) {
            throw new TopicNullException();
        }
        if (generateMaterialDTO.level() == null) {
            throw new LevelNullException();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("topic", generateMaterialDTO.topic());
        params.put("level", generateMaterialDTO.level());
        params.put("interests", generateMaterialDTO.interests());

        try {
            // Delega para o gerador de material de inglês
            return englishMaterialGeneratorService.generateMainActivity(params);
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    public String generateStudentActivity(GenerateStudentActivityDTO studentActivityDTO) {
        List<InterestsEnum> interests = new ArrayList<>();
        ProficiencyLevel level;
        StudentResponseDTO student = studentService.findStudentDTO(studentActivityDTO.studentId());

        if (studentActivityDTO.interests() != null && studentActivityDTO.interests()) {
            interests.addAll(student.interests());
        }

        if (studentActivityDTO.level() != null && studentActivityDTO.level()) {
            level = student.proficiencyLevel();
        } else {
            level = studentActivityDTO.setLevel();
            if (level == null) {
                throw new LevelNullException();
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentActivityDTO.studentId());
        params.put("subject", studentActivityDTO.subject());
        // `studentActivityDTO.interests()` is a Boolean. We need to pass the actual `interests` data.
        // Assuming InterestsEnum values are expected by the prompt template
        params.put("interests", interests.stream().map(InterestsEnum::getCode).toList()); // Pass codes for mapping in generator
        params.put("level", level);

        try {
            return englishMaterialGeneratorService.generateMainActivity(params); // Delegar
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    public String generateStudentReport(GenerateStudentReportDTO generateStudentReportDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("data", generateStudentReportDTO.data());
        // O campo 'clientId' no prompt é usado para identificação, mas aqui 'data' é uma lista.
        // Se `clientId` é o ID do estudante, ele precisa ser passado separadamente.
        // Por enquanto, vamos assumir que `generateStudentReportDTO.data()` é suficiente para o prompt.
        // Se precisar do studentId, adicione ao DTO e ao params.
        // params.put("clientId", generateStudentReportDTO.getStudentId()); // Exemplo se o DTO tivesse studentId

        try {
            return englishMaterialGeneratorService.generateReport(params); // Delegar
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    public String generateExercise(GenerateExerciseDTO generateExerciseDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("topic", generateExerciseDTO.topic());
        params.put("level", generateExerciseDTO.level());
        // Seu GenerateExerciseDTO não tem "exerciseType", assumindo que EnglishMaterialGeneratorService lida com isso implicitamente
        params.put("quantity", generateExerciseDTO.quantity());
        params.put("interests", generateExerciseDTO.interests());

        try {
            return englishMaterialGeneratorService.generateMainActivity(params); // Delegar
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    public String generateAncillaryContent(Map<String, Object> ancillaryParams) {
        try {
            return englishMaterialGeneratorService.generateAncillaryContent(ancillaryParams); // Delegar
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }
}