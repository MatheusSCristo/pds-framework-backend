package com.neo_educ.backend.core.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class SessionEntity<U extends AbstractModel> extends AbstractModel {

    private String title;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    protected U owner;
    
}