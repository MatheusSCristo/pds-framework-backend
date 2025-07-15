package com.neo_educ.backend.apps.nutrition.auth.controllers;

import com.neo_educ.backend.apps.nutrition.auth.dto.NutritionistLoginResponseDTO;
import com.neo_educ.backend.apps.nutrition.auth.dto.NutritionistRegisterDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.dto.NutritionistResponseDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.mappers.NutritionistMapper;
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
@RequestMapping("/auth/nutritionist")
public class NutritionistAuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private NutritionistMapper nutritionistMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("nutritionFactory")
    private ApplicationFactory appFactory;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody NutritionistRegisterDTO registerDTO) {
        AuthService authService = appFactory.createAuthService();

        NutritionistEntity nutritionist = nutritionistMapper.toEntity(registerDTO);
        
        authService.signUp(nutritionist);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<NutritionistLoginResponseDTO> signIn(@RequestBody UserLoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );

        AuthService authService = appFactory.createAuthService();
        
        NutritionistEntity authenticatedUser = (NutritionistEntity) authService.signIn(loginDTO);
        
        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expiresIn = jwtService.getExpirationTime();
        
        NutritionistResponseDTO authenticatedDTO = nutritionistMapper.toDTO(authenticatedUser);
        
        NutritionistLoginResponseDTO loginResponse = new NutritionistLoginResponseDTO(jwtToken, expiresIn, authenticatedDTO);

        return ResponseEntity.ok(loginResponse);
    }
}