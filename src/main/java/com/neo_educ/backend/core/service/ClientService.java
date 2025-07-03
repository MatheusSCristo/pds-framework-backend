package com.neo_educ.backend.core.service;

import java.util.List;

/**
 * Interface genérica (contrato) para serviços que gerenciam 'Clientes'.
 * Simplificada para usar o mesmo DTO para criação e atualização.
 *
 * @param <E> A classe da Entidade concreta (ex: Student)
 * @param <C> O DTO de Criação (ex: StudentRegisterDTO)
 * @param <R> O DTO de Resposta (ex: StudentResponseDTO)
 */
public interface ClientService<E, C, R> {

    /**
     * Cria um novo cliente.
     */
    R create(C createDto, Long UserId);

    /**
     * Busca um cliente pelo seu ID.
     */
    R findById(Long id);

    /**
     * Busca todos os clientes de um determinado usuario.
     */
    List<R> findAll(Long userId);

    /**
     * Deleta um cliente pelo seu ID.
     */
    void delete(Long id);
}