package com.neo_educ.backend.core.service;

import java.util.List;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;

import jakarta.validation.Valid;

/**
 * Interface genérica (contrato) para qualquer serviço que gerencie
 * um tipo de 'Sessão'.
 *
 * @param <E> A classe da Entidade concreta (ex: ClassPlan)
 * @param <C> O DTO de Criação (ex: ClassPlanCreateDTO)
 * @param <R> O DTO de Resposta (ex: ClassPlanResponseDTO)
 */
public interface SessionService<E, C, R> {

    /**
     * Cria uma nova sessão.
     * @param createDto DTO com os dados de criação.
     * @param userId ID do "dono" da sessão (ex: teacherId, nutritionistId).
     * @return O DTO de resposta da entidade criada.
     */
    R create(C createDto, Long userId);

    /**
     * Busca uma sessão pelo seu ID.
     * @param id O ID da sessão.
     * @return O DTO de resposta.
     */
    R findByID(Long id);

    /**
     * Busca todas as sessões de um determinado "dono".
     * @param userId O ID do dono.
     * @return Uma lista de DTOs de resposta.
     */
    List<R> findAll(Long userId);

    /**
     * Deleta uma sessão pelo seu ID.
     * @param id O ID da sessão a ser deletada.
     */
    void delete(Long id);

    ClassPlansResponseDTO create(ClassPlansCreateDTO dto, Long teacherId);

    // Você poderia adicionar outros métodos comuns aqui, como update().
}