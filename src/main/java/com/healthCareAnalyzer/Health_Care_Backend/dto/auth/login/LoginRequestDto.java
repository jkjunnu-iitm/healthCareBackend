package com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank
    @Email(message = "Invalid email id")
    private String username;
    @NotBlank
    @Size(min = 4, max = 20, message = "Min password length 4")
    private String password;
}
