package com.neo_educ.backend.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class UserController<T, D> {

    protected abstract D mapToDto(T entity);

    @GetMapping("/me")
    public ResponseEntity<D> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        @SuppressWarnings("unchecked")
        T currentUser = (T) authentication.getPrincipal();
        return ResponseEntity.ok(mapToDto(currentUser));
    }
}