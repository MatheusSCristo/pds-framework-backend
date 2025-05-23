package com.neo_educ.backend.modules.auth.controllers;

import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.LoginResponseDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.auth.service.AuthService;
import com.neo_educ.backend.modules.teacher.dto.TeacherDTO;
import com.neo_educ.backend.modules.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
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
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<TeacherEntity> signUp(@RequestBody RegisterDTO registerDTO) {
        TeacherEntity teacher = authService.signUp(registerDTO);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody LoginDTO loginDTO) {
        TeacherEntity authenticatedUser=authService.signIn(loginDTO);
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
