package com.healthCareAnalyzer.Health_Care_Backend.utility;

import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ExtractUsernameFromToken {

    private final JwtService jwtService;

    public ExtractUsernameFromToken(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String extractUsernameFromToken(HttpServletRequest request) {
        String authHeader;
        authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtService.extractUsername(token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}
