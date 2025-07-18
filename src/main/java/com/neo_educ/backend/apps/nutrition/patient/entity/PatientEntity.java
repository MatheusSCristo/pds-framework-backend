package com.neo_educ.backend.apps.nutrition.patient.entity;

import com.neo_educ.backend.apps.nutrition.mealPlan.entity.MealPlanEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.patient.enums.NutritionalGoal;
import com.neo_educ.backend.core.model.ClientEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "patient")
@Table(name = "nutrition_patients")
public class PatientEntity extends ClientEntity<NutritionistEntity> {

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String anthropometricData;

    @NotEmpty(message = "Pelo menos um objetivo nutricional deve ser fornecido.")
    @ElementCollection(targetClass = NutritionalGoal.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "patient_goals", joinColumns = @JoinColumn(name = "patient_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "goal", nullable = false)
    private Set<NutritionalGoal> nutritionalGoals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    @Override
    public NutritionistEntity getOwner() {
        return super.getOwner();
    }

    @Override
    public void setOwner(NutritionistEntity owner) {
        super.setOwner(owner);
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MealPlanEntity> mealPlans = new ArrayList<>();
}