package com.neo_educ.backend.modules.materialGeneration.utils;

import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import org.springframework.stereotype.Component;

@Component
public class EnglishSetencesPromptTemplate {

    public String createPrompt(GenerateMaterialDTO infos) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente especializado em ensino de inglês. ")
                .append("Sua tarefa é gerar uma lista de 10 frases em inglês para prática de conversação, ")
                .append("baseadas no tópico gramatical, nível do aluno e, quando fornecidos, seus interesses pessoais. ")
                .append("O tópico gramatical é ").append(infos.topic()).append(", ")
                .append("o nível do aluno é ").append(infos.level()).append(" ");

        if (infos.interests() != null && !infos.interests().isEmpty()) {
            prompt.append("e os interesses do aluno são ");

            String interestsText = String.join(", ", infos.interests());
            prompt.append(interestsText);
        } else {
            prompt.append("e não foram fornecidos interesses específicos");
        }

        prompt.append(". Adapte rigorosamente a complexidade das frases ao nível do aluno (A1, A2, B1, B2, C1 ou C2) ")
                .append("e concentre-se exclusivamente no tópico gramatical solicitado. ")
                .append("Quando fornecidos os interesses do aluno, incorpore-os nas frases de maneira natural. ")
                .append("Forneça APENAS as frases em inglês numeradas de 1 a 10, sem explicações adicionais ou traduções. ")
                .append("As frases devem ser práticas, úteis para conversação e contextualmente relevantes. ")
                .append("Evite frases extremamente simples ou óbvias para níveis mais avançados. ")
                .append("Por favor, gere exatamente 10 frases em inglês que atendam a esses critérios.");

        return prompt.toString();
    }
}
