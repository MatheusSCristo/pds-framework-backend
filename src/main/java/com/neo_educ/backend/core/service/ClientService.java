package com.neo_educ.backend.core.service;

import java.util.List;


public interface ClientService<E, C, R> {

    R create(C createDto, Long UserId);

    R findById(Long id);

    List<R> findAll(Long userId);

    void delete(Long id);
}