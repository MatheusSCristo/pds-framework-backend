package com.neo_educ.backend.apps.nutrition.mealPlan.entity;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.model.ClientProgressMonitoringEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Representa um Plano Alimentar gerado para um paciente.
 * Esta entidade armazena o conteúdo do plano e o associa a um
 * paciente e a um período de validade (startDate, endDate).
 */
@Entity
@Table(name = "meal_plans")
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MealPlanEntity extends ClientProgressMonitoringEntity {

    /**
     * O título do plano alimentar, pode vir da 'category' do serviço.
     * Ex: "Plano para Perda de Peso - Semana 1".
     */
    @Column(nullable = false)
    private String title;

    /**
     * O conteúdo completo do plano alimentar gerado pela IA.
     * Este é o campo principal para armazenar o resultado do generateActivityContent.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * O paciente para o qual este plano alimentar foi criado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;
}