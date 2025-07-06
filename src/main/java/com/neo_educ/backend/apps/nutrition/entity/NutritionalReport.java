package com.neo_educ.backend.apps.nutrition.entity;

import com.neo_educ.backend.core.model.BaseReport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class NutritionalReport extends BaseReport {

    // Acompanhamento de peso, medidas, etc.
    private String weightMeasuresProgress;

    // Nível de adesão do paciente ao plano alimentar proposto.
    private String planAdherence;

    // Análise gerada sobre os nutrientes.
    private String nutritionalAnalysis;

    /**
     * Implementação do método abstrato para gerar o conteúdo do relatório.
     * Aqui, você pode agregar os dados e formatá-los.
     * @return um objeto contendo os dados do relatório.
     */
    @Override
    public Object generateReport() {
        Map<String, String> reportData = new java.util.HashMap<>();
        reportData.put("weightMeasuresProgress", this.weightMeasuresProgress);
        reportData.put("planAdherence", this.planAdherence);
        reportData.put("nutritionalAnalysis", this.nutritionalAnalysis);
        return reportData;
    }
}