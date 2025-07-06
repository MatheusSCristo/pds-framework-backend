package com.neo_educ.backend.apps.english.auth.controllers;

import com.neo_educ.backend.apps.english.auth.dto.LoginResponseDTO;
import com.neo_educ.backend.apps.english.auth.dto.RegisterDTO;
import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.core.dto.auth.UserLoginDTO;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.AuthService;
import com.neo_educ.backend.core.service.JwtService;
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
@RequestMapping("/auth/teacher")
public class TeacherAuthController {
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

        TeacherEntity user = new TeacherEntity(
                registerDTO.name(),
                registerDTO.lastName(),
                registerDTO.email(),
                registerDTO.password(),
                registerDTO.phone()

        ) ;
        authService.signUp(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody UserLoginDTO loginDTO) {
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
