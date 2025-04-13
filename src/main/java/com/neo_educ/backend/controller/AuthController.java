package com.neo_educ.backend.controller;

import com.neo_educ.backend.dto.auth.LoginDTO;
import com.neo_educ.backend.dto.auth.LoginResponseDTO;
import com.neo_educ.backend.dto.auth.RegisterDTO;
import com.neo_educ.backend.model.TeacherEntity;
import com.neo_educ.backend.service.AuthService;
import com.neo_educ.backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TeacherEntity> register(@RequestBody RegisterDTO registerDTO) {
        TeacherEntity teacher = authService.signup(registerDTO);

        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        TeacherEntity authenticatedUser = authService.authenticate(loginDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn=jwtService.getExpirationTime();

        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,expiresIn
        );

        return ResponseEntity.ok(loginResponse);
    }
}
