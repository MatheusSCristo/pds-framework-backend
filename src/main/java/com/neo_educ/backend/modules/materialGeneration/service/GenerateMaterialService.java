package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.exceptions.generateMaterial.LevelNullException;
import com.neo_educ.backend.exceptions.generateMaterial.TopicNullException;
import com.neo_educ.backend.modules.interests.enums.InterestsEnum;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.modules.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
import com.neo_educ.backend.modules.student.service.StudentService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateMaterialService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        if (generateMaterialDTO.topic() == null) {
            throw new TopicNullException();
        }

        if (generateMaterialDTO.level() == null) {
            throw new LevelNullException();
        }

        String prompt = promptTemplate.createMaterialPrompt(generateMaterialDTO);
        return chatClient.prompt(prompt).call().content();
    }

    public String generateStudentActivity(GenerateStudentActivityDTO studentActivityDTO) {

        List<InterestsEnum> interests = new ArrayList<>();
        ProficiencyLevel level = studentActivityDTO.setLevel();
        StudentResponseDTO student = studentService.findStudent(studentActivityDTO.studentId());
        if (studentActivityDTO.interests()) {
            interests.addAll(student.interests());
        }
        if (studentActivityDTO.level()) {
            level = student.proficiencyLevel();
        }
        String prompt = promptTemplate.createActivityPrompt(interests, level, studentActivityDTO.subject());
        return chatClient.prompt(prompt).call().content();

    }

}
