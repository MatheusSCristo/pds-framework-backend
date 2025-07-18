package com.neo_educ.backend.core.mapper;

public interface SessionMapper<C, R, E> {
    E toEntity(C createDto);
    R toResponse(E entity);
}