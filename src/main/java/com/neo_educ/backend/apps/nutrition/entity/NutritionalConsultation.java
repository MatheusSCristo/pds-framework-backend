package com.neo_educ.backend.apps.nutrition.entity;

import com.neo_educ.backend.core.model.Session;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "nutritional_consultation")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalConsultation extends Session {

    //Tipo da consulta (primeira consulta, retorno, etc.)
    private String consultationType;

    //Detalhes da avaliação feita durante a consulta
    @Column(columnDefinition = "TEXT")
    private String anthropometricAssessment;
}
