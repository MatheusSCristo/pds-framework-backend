package com.neo_educ.backend.core.controller;

import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.service.SessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor 
public abstract class AbstractSessionController<C, R, U extends AbstractModel> {

    private final ApplicationFactory appFactory;

    @SuppressWarnings("unchecked")
    private Long getAuthenticatedOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        U owner = (U) authentication.getPrincipal();
        return owner.getId();
    }

    @SuppressWarnings("unchecked")
    private SessionService<?, C, R, U> getService() {
        return (SessionService<?, C, R, U>) appFactory.createSessionService();
    }

    @PostMapping
    public ResponseEntity<R> create(@Valid @RequestBody C createDto) {
        R response = getService().create(createDto, getAuthenticatedOwnerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> findById(@PathVariable Long id) {
        R response = getService().findByID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<R>> findAll() {
        List<R> response = getService().findAll(getAuthenticatedOwnerId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}