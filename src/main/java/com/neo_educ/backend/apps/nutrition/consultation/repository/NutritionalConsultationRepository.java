package com.neo_educ.backend.apps.nutrition.consultation.repository;

import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import com.neo_educ.backend.core.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NutritionalConsultationRepository extends AbstractRepository<NutritionalConsultationEntity> {

    List<NutritionalConsultationEntity> findAllByOwnerId(Long ownerId);

    @Query("SELECT COUNT(nc) FROM NutritionalConsultationEntity nc WHERE nc.owner.id = :ownerId AND nc.date BETWEEN :start AND :end")
    Long countConflictingConsultations(
            @Param("ownerId") Long ownerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}