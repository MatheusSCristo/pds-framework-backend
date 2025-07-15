package com.neo_educ.backend.apps.nutrition.patient.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

import com.neo_educ.backend.apps.nutrition.mealPlan.entity.MealPlanEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.patient.enums.NutritionalGoal;
import com.neo_educ.backend.core.model.ClientEntity;

@EqualsAndHashCode(callSuper = true) // Importante para entidades que herdam
@Data // Combina @Getter, @Setter, @ToString, etc.
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "patient")
@Table(name = "nutrition_patients")
public class PatientEntity extends ClientEntity {

    // Lista de alergias
    @Column(columnDefinition = "TEXT")
    private String allergies;

    // Dados como peso, altura, IMC, etc.
    @Column(columnDefinition = "TEXT")
    private String anthropometricData;

    // Metas do paciente (paralelo aos 'interests' do estudante)
    @ElementCollection(targetClass = NutritionalGoal.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "patient_goals", joinColumns = @JoinColumn(name = "patient_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private Set<NutritionalGoal> nutritionalGoals;

    // Relação com o profissional responsável
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private NutritionistEntity nutritionist;

    // Relação com os planos alimentares do paciente (paralelo às 'activities' do estudante)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealPlanEntity> mealPlans;
}
