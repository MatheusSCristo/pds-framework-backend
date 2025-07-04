package com.neo_educ.backend.core.service;

import java.util.List;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;

import jakarta.validation.Valid;
public interface SessionService<E, C, R> {


    R create(C createDto, Long userId);

    R findByID(Long id);

    List<R> findAll(Long userId);

    void delete(Long id);

    ClassPlansResponseDTO create(ClassPlansCreateDTO dto, Long teacherId);

}