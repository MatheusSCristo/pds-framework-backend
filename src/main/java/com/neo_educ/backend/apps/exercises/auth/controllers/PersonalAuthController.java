package com.neo_educ.backend.apps.exercises.auth.controllers;

import com.neo_educ.backend.apps.exercises.auth.dto.RegisterDTO;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.mappers.PersonalMapper;
import com.neo_educ.backend.core.dto.auth.LoginResponseDTO;
import com.neo_educ.backend.core.dto.auth.UserLoginDTO;
import com.neo_educ.backend.core.dto.user.UserResponseDTO;
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
@RequestMapping("/auth/personal")
public class PersonalAuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("exercisesFactory")
    private ApplicationFactory appFactory;
    @Autowired
    private PersonalMapper personalMapper;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody RegisterDTO registerDTO) {
        AuthService authService = appFactory.createAuthService();

        PersonalEntity user = new PersonalEntity(
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

        PersonalEntity authenticatedUser = (PersonalEntity) authService.signIn(loginDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn = jwtService.getExpirationTime();
        UserResponseDTO authenticatedDTO = personalMapper.toDTO(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, expiresIn, authenticatedDTO);

        return ResponseEntity.ok(loginResponse);
    }
}
