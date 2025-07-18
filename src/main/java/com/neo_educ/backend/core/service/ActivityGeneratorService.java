package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <D> O tipo do DTO para gerar uma atividade.
 * @param <R> O tipo do DTO para gerar um relatório.
 * @param <M> O tipo do DTO para gerar material de apoio (como listas de compras ou frases).
 * @param <E> O tipo do DTO para gerar um exercício estruturado.
 */
public abstract class ActivityGeneratorService<D, R, M, E> {

    @Autowired
    private LLMService llmService;

    // ----- Template methods -----

    public final String generateSession(String topic) {
        try {
            String prompt = buildPromptForSession(topic);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public final String generateActivityContent(D activityDto) {
        try {
            String prompt = buildPromptForActivity(activityDto);

            if (prompt == null) {
                return "Não foi possível gerar a atividade para os dados fornecidos.";
            }

            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public final String generateReportContent(R reportDto) {
        try {
            String prompt = buildPromptForReport(reportDto);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public final String generateExerciseContent(E exerciseDto) {
        try {
            String prompt = buildPromptForExercise(exerciseDto);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public final String generateMaterialContent(M materialDto) {
        try {
            String prompt = buildPromptForMaterial(materialDto);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    // ----- Pontos de extensão (o que as instâncias precisam implementar) -----

    protected abstract String buildPromptForSession(String topic);
    protected abstract String buildPromptForActivity(D activityDto);
    protected abstract String buildPromptForReport(R reportDto);
    protected abstract String buildPromptForExercise(E exerciseDto);
    protected abstract String buildPromptForMaterial(M materialDto);
}