package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.exceptions.generateMaterial.LevelNullException;
import com.neo_educ.backend.exceptions.generateMaterial.TopicNullException;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.utils.EnglishSetencesPromptTemplate;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateMaterialService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        if(generateMaterialDTO.topic() == null) {
            throw new TopicNullException();
        }

        if(generateMaterialDTO.level() == null) {
            throw new LevelNullException();
        }

        String prompt  = promptTemplate.createPrompt(generateMaterialDTO);
        return chatClient.prompt(prompt).call().content();
    }

}
