package com.neo_educ.backend.core.controller;

import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractClientController<C, R> {

    private final ApplicationFactory appFactory;

    @SuppressWarnings("unchecked")
    private ClientService<?, C, R, ?> getService() {
        return (ClientService<?, C, R, ?>) appFactory.createClientService();
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<R> create(@RequestBody C createDto) {
        R newClient = getService().create(createDto, getAuthenticatedUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping
    public ResponseEntity<List<R>> findAll() {
        List<R> clients = getService().findAll(getAuthenticatedUserId());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> findById(@PathVariable Long id) {
        R client = getService().findById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}