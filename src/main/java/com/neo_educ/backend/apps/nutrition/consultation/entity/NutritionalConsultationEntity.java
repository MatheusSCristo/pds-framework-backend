package com.neo_educ.backend.apps.nutrition.consultation.entity;

import java.time.LocalDateTime;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.core.model.Session;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "nutritional_consultation")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalConsultationEntity extends Session {

    //Tipo da consulta (primeira consulta, retorno, etc.)
    private String consultationType;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private NutritionistEntity nutritionist;

    public NutritionalConsultationEntity(String title, String consultationType, LocalDateTime createdAt, NutritionistEntity nutritionist) {
        super.setTitle(title);
        super.setDate(createdAt);
        this.consultationType = consultationType;
        this.nutritionist = nutritionist;
    }
}
