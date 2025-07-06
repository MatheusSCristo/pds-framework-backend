package com.neo_educ.backend.apps.nutrition.llm.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NutritionPromptTemplate {

    /**
     * Estou usando um hashmap inicialmente, mas pode adaptar para utilizar um DTO quando criar as controllers
     * Prompt para gerar um plano alimentar personalizado
     * @param patientData um Map contendo dados como objetivos, restrições, etc.
     * @return O prompt formatado para a IA.
     */
    public String createMealPlanPrompt(Map<String, String> patientData) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente de nutricionista altamente qualificado. ")
                .append("Sua tarefa é criar um plano alimentar semanal detalhado e personalizado para um paciente ")
                .append("com base nas características fornecidas.\n\n")

                .append("## Dados do Paciente:\n")
                .append("- Objetivo: ").append(patientData.getOrDefault("nutritionalGoals", "Não informado")).append("\n")
                .append("- Restrições alimentares: ").append(patientData.getOrDefault("allergies", "Nenhuma")).append("\n")
                .append("- Dados antropométricos: ").append(patientData.getOrDefault("anthropometricData", "Não informado")).append("\n")
                .append("- Histórico médico: ").append(patientData.getOrDefault("medicalHistory", "Não informado")).append("\n\n")

                .append("## Diretrizes para o Plano:\n")
                .append("- Crie um plano para 7 dias completos (segunda a domingo)\n")
                .append("- Inclua 5 refeições diárias: café da manhã, lanche da manhã, almoço, lanche da tarde e jantar\n")
                .append("- Forneça porções sugeridas para cada alimento\n")
                .append("- Inclua dicas de preparação quando necessário\n")
                .append("- Considere as restrições alimentares rigorosamente\n")
                .append("- Adapte as calorias e nutrientes ao objetivo do paciente\n")
                .append("- Varie os alimentos ao longo da semana\n\n")

                .append("## Formato da Resposta:\n")
                .append("Organize o plano de forma clara e estruturada, pronto para ser entregue ao paciente. ")
                .append("Use seções bem definidas para cada dia da semana e cada refeição. ")
                .append("A resposta deve ser prática e de fácil compreensão.");

        return prompt.toString();
    }

    /**
     * Prompt para gerar uma lista de compras baseada em um plano alimentar.
     *
     * @param mealPlan O texto do plano alimentar.
     * @return O prompt formatado para a IA.
     */
    public String createShoppingListPrompt(String mealPlan) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente especializado em organização de compras para nutrição. ")
                .append("Sua tarefa é analisar o plano alimentar fornecido e gerar uma lista de compras ")
                .append("completa e bem organizada.\n\n")

                .append("## Plano Alimentar:\n")
                .append("--- INÍCIO DO PLANO ---\n")
                .append(mealPlan).append("\n")
                .append("--- FIM DO PLANO ---\n\n")

                .append("## Diretrizes para a Lista:\n")
                .append("- Organize por categorias (Frutas, Vegetais, Proteínas, Grãos, Laticínios, Temperos, etc.)\n")
                .append("- Calcule quantidades aproximadas para a semana\n")
                .append("- Elimine duplicatas e agrupe itens similares\n")
                .append("- Considere praticidade de armazenamento\n")
                .append("- Inclua apenas itens necessários para o plano\n\n")

                .append("## Formato da Resposta:\n")
                .append("Apresente a lista de forma clara e organizada, pronta para ser usada durante as compras. ")
                .append("Use categorias bem definidas e quantidades práticas.");

        return prompt.toString();
    }

    /**
     * Novamente, estou usando um hashmap inicialmente, mas pode adaptar para utilizar um DTO quando criar as controllers
     * Prompt para gerar uma análise nutricional detalhada.
     *
     * @param patientData dados do paciente
     * @param consultationData dados da consulta
     * @return O prompt formatado para a IA.
     */
    public String createNutritionalAnalysisPrompt(Map<String, String> patientData, Map<String, String> consultationData) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um nutricionista experiente especializado em análise nutricional. ")
                .append("Sua tarefa é gerar uma análise detalhada do progresso e status nutricional do paciente ")
                .append("com base nos dados fornecidos.\n\n")

                .append("## Dados do Paciente:\n");

        patientData.forEach((key, value) ->
                prompt.append("- ").append(key).append(": ").append(value).append("\n")
        );

        prompt.append("\n## Dados da Consulta:\n");

        consultationData.forEach((key, value) ->
                prompt.append("- ").append(key).append(": ").append(value).append("\n")
        );

        prompt.append("\n## Diretrizes para a Análise:\n")
                .append("- Avalie o progresso em relação aos objetivos estabelecidos\n")
                .append("- Identifique pontos positivos e áreas que precisam de atenção\n")
                .append("- Forneça recomendações específicas e práticas\n")
                .append("- Considere aspectos bioquímicos, antropométricos e comportamentais\n")
                .append("- Mantenha linguagem acessível ao paciente\n\n")

                .append("## Formato da Resposta:\n")
                .append("Estruture a análise em seções claras: Progresso Atual, Pontos Positivos, ")
                .append("Áreas de Melhoria, Recomendações e Próximos Passos.");

        return prompt.toString();
    }

}
