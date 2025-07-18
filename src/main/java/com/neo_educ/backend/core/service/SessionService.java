package com.neo_educ.backend.core.service;

import com.neo_educ.backend.core.mapper.SessionMapper;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.SessionEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionService<
        E extends SessionEntity<U>, 
        C, 
        R, 
        U extends AbstractModel
> {
    JpaRepository<E, Long> getRepository();
    SessionMapper<C, R, E> getMapper();
    JpaRepository<U, Long> getOwnerRepository();
    
    R create(C createDto, Long userId);
    List<R> findAll(Long userId);

    default R findByID(Long id) {
        E sessionEntity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão com ID " + id + " não encontrada."));
        return getMapper().toResponse(sessionEntity);
    }

    default void delete(Long id) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Sessão com ID " + id + " não encontrada para exclusão.");
        }
        getRepository().deleteById(id);
    }
}