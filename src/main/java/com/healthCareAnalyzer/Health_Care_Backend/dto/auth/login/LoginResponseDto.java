package com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login;

import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private UserEntity user;
}
