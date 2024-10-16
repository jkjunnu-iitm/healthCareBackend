package com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private String roles;
}
