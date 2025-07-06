package com.neo_educ.backend.apps.nutrition.entity;

import com.neo_educ.backend.core.model.ClientEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "nutrition_patients")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity extends ClientEntity {

    //@Column(columnDefinition = "TEXT") permite armazenar strings longas no banco
    //Por padrão, a String é um varchar(255), @Column(columnDefinition = "TEXT") permite extrapolar tal tamanho

    // Histórico médico do paciente
    @Column(columnDefinition = "TEXT")
    private String medicalHistory;

    // Lista de alergias
    @Column(columnDefinition = "TEXT")
    private String allergies;

    //Metas do paciente (perda de peso, ganho  de massa, etc
    private String nutritionalGoals;

    // Dados como peso, altura, IMC, etc.
    @Column(columnDefinition = "TEXT")
    private String anthropometricData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private NutritionistEntity nutritionist;

}
