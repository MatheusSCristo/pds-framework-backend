// src/main/java/com/neo_educ/backend/modules/auth/controllers/AuthController.java
package com.neo_educ.backend.modules.auth.controllers;

import com.neo_educ.backend.core.service.AuthServiceInterface;
import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.LoginResponseDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.modules.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; // Para obter o principal
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // Remove {applicationType}
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServiceInterface authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody RegisterDTO registerDTO) {
        authService.signUp(registerDTO); // Usa o AuthService diretamente
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );
        TeacherEntity authenticatedUser = (TeacherEntity) authentication.getPrincipal();

        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn=jwtService.getExpirationTime();
        TeacherResponseDTO authenticatedDTO=teacherMapper.toDTO(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,expiresIn,authenticatedDTO
        );

        return ResponseEntity.ok(loginResponse);
    }
}