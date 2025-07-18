package com.neo_educ.backend.configs;

import com.neo_educ.backend.core.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final Map<String, UserDetailsService> userDetailsServices;

    public JwtAuthFilter(
            JwtService jwtService,
            HandlerExceptionResolver handlerExceptionResolver,
            Map<String, UserDetailsService> userDetailsServices
    ) {
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userDetailsServices = userDetailsServices;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);
            final String role = jwtService.extractRole(jwt); // <-- EXTRAI O PAPEL DO TOKEN

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                // ESCOLHE O UserDetailsService CORRETO COM BASE NO PAPEL
                UserDetailsService userDetailsService = getUserDetailsServiceForRole(role);
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
    
    // MÉTODO AUXILIAR PARA ESCOLHER O SERVIÇO
    private UserDetailsService getUserDetailsServiceForRole(String role) {
        if ("TEACHER".equals(role)) {
            return userDetailsServices.get("teacherService");
        } else if ("NUTRITIONIST".equals(role)) {
            return userDetailsServices.get("nutritionistService");
        } else if ("PERSONAL".equals(role)) {
            return userDetailsServices.get("personalService");
        }
        throw new IllegalArgumentException("Papel (role) inválido no token: " + role);
    }
}
