package com.neo_educ.backend.core.service;

import java.util.List;
public interface SessionService<E, C, R> {


    R create(C createDto, Long userId);

    R findByID(Long id);

    List<R> findAll(Long userId);

    void delete(Long id);

}