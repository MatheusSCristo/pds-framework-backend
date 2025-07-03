package com.neo_educ.backend.apps.english.auth.controllers;

import com.neo_educ.backend.apps.english.auth.dto.LoginDTO;
import com.neo_educ.backend.apps.english.auth.dto.LoginResponseDTO;
import com.neo_educ.backend.apps.english.auth.dto.RegisterDTO;
import com.neo_educ.backend.apps.english.jwt.service.JwtService;
import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("englishFactory")
    private ApplicationFactory appFactory;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody RegisterDTO registerDTO) {
        AuthService authService = appFactory.createAuthService();

        TeacherEntity newTeacher = teacherMapper.toEntity(registerDTO);

        authService.signUp(newTeacher);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );

        AuthService authService = appFactory.createAuthService();
        
        TeacherEntity authenticatedUser = (TeacherEntity) authService.signIn(loginDTO);
        
        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn = jwtService.getExpirationTime();
        TeacherResponseDTO authenticatedDTO = teacherMapper.toDTO(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, expiresIn, authenticatedDTO);

        return ResponseEntity.ok(loginResponse);
    }
}
