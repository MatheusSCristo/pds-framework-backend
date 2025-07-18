package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.mapper.ClientMapper;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.ClientEntity;
import com.neo_educ.backend.core.repository.AbstractClientRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientService<E extends ClientEntity<U>, C, R, U extends AbstractModel> {

    AbstractClientRepository<E> getRepository();
    ClientMapper<C, R, E> getModelMapper();
    JpaRepository<U, Long> getOwnerRepository();

    R create(C createDto, Long ownerId);

    default List<R> findAll(Long ownerId) {
        return getRepository().findAllByOwnerId(ownerId)
                .stream()
                .map(getModelMapper()::toResponse)
                .toList();
    }

    default R findById(Long id) {
        E clientEntity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recurso com ID " + id + " não encontrado."));
        return getModelMapper().toResponse(clientEntity);
    }

    

    default void delete(Long id) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Recurso com ID " + id + " não encontrado para exclusão.");
        }
        getRepository().deleteById(id);
    }
}