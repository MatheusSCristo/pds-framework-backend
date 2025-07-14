package com.neo_educ.backend.apps.exercises.athleteActivity.entity;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.core.model.ClientProgressMonitoringEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "athlete_activity")
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AthleteActivityEntity extends ClientProgressMonitoringEntity {
    @Column(columnDefinition = "TEXT")
    private String activityDescription;
    private String activityType;
    private Double performanceMetricValue;
    private String performanceMetricUnit;
    @ManyToOne
    private AthleteEntity athlete;

}
