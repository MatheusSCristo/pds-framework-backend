package com.neo_educ.backend.apps.exercises.materialGeneration.utils;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.enums.InterestsEnum;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteActivityDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.MetricsAverageBySubject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonalSentencesPromptTemplate {

    public String createWorkoutprompt(String topic) {
        return "Você é um assistente para personal trainers. Quando o personal digitar um assunto (por exemplo: Treino de Força para Iniciantes ou Rotina de Cardio para Emagrecimento), você deve gerar um plano de treino completo para um atleta. O plano deve ser objetivo, prático e adaptado aos objetivos do atleta. O personal pode usar esse plano diretamente em suas sessões.\n"
                + "\n"
                + "O plano deve conter:\n"
                + "- Objetivo(s) do treino\n"
                + "- Equipamentos necessários (se houver)\n"
                + "- Duração estimada\n"
                + "- Estrutura do treino (com sugestões de aquecimento, parte principal e desaquecimento)\n"
                + "- Dicas para o personal trainer\n"
                + "- Sugestões de atividades complementares ou recuperação (opcional)\n"
                + "Esse é o assunto: " + topic;
    }

    public String createMaterialPrompt(GenerateMaterialDTO infos) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente especializado em criação de materiais para treinos. ")
                .append("Sua tarefa é gerar uma lista de 10 exercícios ou dicas de treino, ")
                .append("baseadas no tipo de treino/foco, nível do atleta e, quando fornecidos, seus interesses pessoais. ")
                .append("O tipo de treino/foco é ").append(infos.topic()).append(", ")
                .append("o nível do atleta é ").append(infos.level()).append(" ");

        if (infos.goals() != null && !infos.goals().isEmpty()) {
            prompt.append("e os interesses do atleta são ");

            String interestsText = String.join(", ", infos.goals());
            prompt.append(interestsText);
        } else {
            prompt.append("e não foram fornecidos interesses específicos");
        }

        prompt.append(". Adapte rigorosamente a complexidade dos exercícios/dicas ao nível do atleta (BEGGINER, INTERMEDIATE, ADVANCED, ELITE) ")
                .append("e concentre-se exclusivamente no tipo de treino/foco solicitado. ")
                .append("Quando fornecidos os interesses do atleta, incorpore-os nos exercícios/dicas de maneira natural. ")
                .append("Forneça APENAS os exercícios ou dicas de treino numeradas de 1 a 10, sem explicações adicionais ou traduções. ")
                .append("As sugestões devem ser práticas, úteis para o treinamento e contextualmente relevantes. ")
                .append("Evite sugestões extremamente simples ou óbvias para níveis mais avançados. ")
                .append("Por favor, gere exatamente 10 exercícios ou dicas de treino que atendam a esses critérios.");

        return prompt.toString();
    }

    public String createActivityPrompt(GenerateAthleteActivityDTO activityCreateDTO, AthleteResponseDTO athlete) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente especializado em atividades físicas. ")
                .append("Sua tarefa é gerar uma lista de atividades ou rotinas de treino para um atleta, ")
                .append("baseadas no foco da atividade, nível do atleta e, quando fornecidos, seus interesses pessoais. ")
                .append("O foco da atividade é ").append(activityCreateDTO.topic()).append(", ");
                if(activityCreateDTO.level()){
                prompt.append("o nível do atleta é ").append(athlete.workoutLevel()).append(" ");
                }
                else{
                    prompt.append("o nivel das atividades devem ser ").append(activityCreateDTO.workoutLevel()).append(", ");
                }

                if(activityCreateDTO.interests()){
                    if (athlete.interests() != null && !athlete.interests().isEmpty()) {
                        prompt.append("e os objetivos do atleta são ");
                        String interestsText = String.join(", ", athlete.interests().stream().map(InterestsEnum::toString).toList());
                        prompt.append(interestsText);

        } }else {
            prompt.append("e não foram fornecidos objetivos específicos");
        }



        prompt.append(". Adapte rigorosamente a complexidade das atividades ao nível do atleta (BEGGINER, INTERMEDIATE, ADVANCED, ELITE) ")
                .append("e concentre-se exclusivamente no foco da atividade solicitado. ")
                .append("Quando fornecidos os interesses do atleta, incorpore-os nas atividades de maneira natural. ")
                .append("Forneça APENAS as atividades ou rotinas de treino numeradas de 1 a 10, sem explicações adicionais ou traduções. ")
                .append("As atividades devem ser práticas, úteis para o desempenho e contextualmente relevantes. ")
                .append("Evite atividades extremamente simples ou óbvias para níveis mais avançados. ")
                .append("Por favor, gere exatamente 10 atividades ou rotinas de treino que atendam a esses critérios.")
                .append("Por favor, não escreva um texto de apresentação das atividades, apenas envie as 10 atividades.");

        return prompt.toString();
    }

    public String createReportPrompt(List<MetricsAverageBySubject> averagesBySubject) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um orientador de performance física, especializado em ajudar atletas a melhorarem seu desempenho. ")
                .append("Sua tarefa é analisar o progresso de um atleta por tipo de atividade ou grupo muscular e, com base nisso, sugerir exercícios, técnicas de treino e dicas gerais para otimizar o desempenho. ")
                .append("Considere também fornecer orientações sobre recuperação, nutrição e hábitos saudáveis para o treino. ")
                .append("Responda seguindo exatamente o modelo abaixo, com seções claras para que a resposta seja exibida no frontend. Evite qualquer introdução ou comentário fora desse modelo. ")
                .append("Adote uma linguagem clara, objetiva e motivadora. Não faça críticas negativas.\n\n");

        prompt.append("Resumo do progresso do atleta por categoria:\n");

        for (MetricsAverageBySubject grade : averagesBySubject) {
            prompt.append("- ").append(grade.topic()).append(": média de desempenho ").append(grade.average()).append("\n");
        }

        prompt.append("\nPor favor, responda exatamente conforme o seguinte modelo:\n\n");

        prompt.append("### 📝 Análise Geral de Performance\n")
                .append("> [Breve análise sobre o desempenho do atleta, apontando pontos fortes e pontos de atenção.]\n\n")

                .append("### 📌 Recomendações por Tipo de Treino/Grupo Muscular\n")
                .append("**[Nome da Categoria/Grupo Muscular] - Média de Desempenho: [média]**\n")
                .append("> [Sugestões de exercícios, recursos ou técnicas para otimizar o desempenho nesta categoria.]\n\n")
                .append("(Repita este bloco para cada categoria, especialmente destacando as com médias mais baixas ou áreas de foco)\n\n")

                .append("### 🧠 Técnicas de Treino Recomendadas\n")
                .append("> [Sugestões gerais de técnicas de treino que podem ajudar o atleta, como periodização, progressão de carga, técnicas de execução, etc.]\n\n")

                .append("### ⏰ Recomendações de Recuperação e Hábitos Saudáveis\n")
                .append("> [Dicas sobre como o atleta pode otimizar sua recuperação, planejar sua rotina de treinos, manter hábitos saudáveis de sono e nutrição.]\n\n")
                .append("IMPORTANTE: Formate sua resposta com quebras de linha claras. Após cada cabeçalho (###), dê uma linha em branco. Cada dica deve estar separada por uma nova linha. NÃO escreva todas as dicas em um único parágrafo. Formatação limpa é essencial para exibição no frontend.")
                .append("Por favor, siga rigorosamente este modelo na resposta. Não inclua textos fora dessas seções.");

        return prompt.toString();
    }

    public String createExercisePrompt(GenerateExerciseDTO infos) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um especialista em planejamento de treinos e exercícios para atletas. ")
                .append("Sua tarefa é criar exercícios práticos e eficazes para um atleta.\n\n")

                .append("## Instruções:\n\n")
                .append("Gere ").append(infos.quantity())
                .append(" exercícios focados no tópico ").append(infos.topic())
                .append(" para um atleta de nível ").append(infos.level()).append(".\n\n");

        if (infos.interests() != null && !infos.interests().isEmpty()) {
            prompt.append("Interesses do atleta: ");
            String interests = String.join(", ", infos.interests());
            prompt.append(interests).append("\n\n");
        }

        prompt.append("## Diretrizes importantes:\n\n")
                .append("- Adapte a complexidade e o volume rigorosamente ao nível especificado (BEGGINER, INTERMEDIATE, ADVANCED, ELITE)\n")
                .append("- Varie os tipos de exercícios: levantamento de peso, cardio, funcional, alongamento, etc.\n")
                .append("- Se interesses específicos foram fornecidos, incorpore-os de forma natural nos contextos dos exercícios\n")
                .append("- Mantenha o foco no tópico de treino solicitado (e.g., grupo muscular, tipo de movimento)\n")
                .append("- Forneça instruções claras em português para cada exercício, incluindo séries, repetições e dicas de execução\n")
                .append("- Numere os exercícios sequencialmente\n")
                .append("- IMPORTANTE: Para exercícios que solicitem a descrição de um treino completo ou uma rotina detalhada, forneça APENAS o enunciado da questão. NÃO gere linhas em branco ou espaços para escrita. Ex: 'Descreva um treino de corpo inteiro'.\n")
                .append("- Para exercícios que requerem preenchimento de informações específicas (e.g., número de séries/repetições), você pode usar espaços em branco (_______) ou colchetes [ ] normalmente.\n\n")

                .append("## Formato de resposta OBRIGATÓRIO:\n\n")
                .append("FORMATAÇÃO CRÍTICA: \n")
                .append("- NÃO deixe linha em branco entre o cabeçalho do exercício (ex: 'Exercício 1) ...') e suas alternativas/conteúdo\n")
                .append("- DEIXE uma linha em branco APENAS entre exercícios diferentes\n")
                .append("- O cabeçalho do exercício deve estar IMEDIATAMENTE seguido por suas instruções/detalhes na linha seguinte\n\n")

                .append("Apresente apenas os exercícios, sem explicações adicionais, introduções ou comentários. Cada exercício deve ter:\n")
                .append("1. Número do exercício\n")
                .append("2. Instrução clara em português\n")
                .append("3. O exercício propriamente dito (sem linhas para escrita quando for uma descrição extensa)\n\n")

                .append("Exemplos de formato CORRETO:\n\n")
                .append("Para exercícios de preenchimento:\n")
                .append("Exercício 1) Preencha os detalhes do exercício:\n")
                .append("a) Agachamento: Séries: [_______] Repetições: [_______]\n")
                .append("b) Flexão: Séries: [_______] Repetições: [_______]\n\n")

                .append("Para exercícios de descrição:\n")
                .append("Exercício 2) Descreva a forma correta de execução para o Levantamento Terra, focando na segurança.\n\n")

                .append("LEMBRE-SE: Não deixe linha em branco entre 'Exercício X)...' e as instruções 'a)', 'b)', etc. ")
                .append("A linha em branco deve existir APENAS entre um exercício completo e o próximo exercício.\n\n")

                .append("Gere os exercícios agora, seguindo rigorosamente essas diretrizes de formatação.");

        return prompt.toString();
    }

}