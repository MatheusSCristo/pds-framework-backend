package com.neo_educ.backend.modules.auth.controllers;

import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.LoginResponseDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.teacher.dto.TeacherDTO;
import com.neo_educ.backend.modules.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.auth.useCase.LoginUseCase;
import com.neo_educ.backend.modules.auth.useCase.SignupUseCase;
import com.neo_educ.backend.modules.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SignupUseCase signupuseCase;

    @Autowired
    private LoginUseCase loginUseCase;

    @PostMapping("/signup")
    public ResponseEntity<TeacherEntity> register(@RequestBody RegisterDTO registerDTO) {
        TeacherEntity teacher = signupuseCase.execute(registerDTO);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        TeacherEntity authenticatedUser=loginUseCase.execute(loginDTO);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );
        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn=jwtService.getExpirationTime();
        TeacherDTO authenticatedDTO=teacherMapper.toDTO(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,expiresIn,authenticatedDTO
        );

        return ResponseEntity.ok(loginResponse);
    }
}
