package com.healthCareAnalyzer.Health_Care_Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Email(message = "Invalid email id")
    private String userName;
    @Size(min = 4, max = 20, message = "Min password length 6")
    private String password;
}
