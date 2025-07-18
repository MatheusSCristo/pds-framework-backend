package com.neo_educ.backend.apps.nutrition.consultation.entity;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.core.model.SessionEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nutritional_consultation")
public class NutritionalConsultationEntity extends SessionEntity<NutritionistEntity> {

    @NotBlank(message = "O tipo da consulta não pode estar em branco.")
    private String consultationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    @Override
    public void setOwner(NutritionistEntity owner) {
        super.setOwner(owner);
    }

}