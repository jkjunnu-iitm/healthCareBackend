package com.healthCareAnalyzer.Health_Care_Backend.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameDto {
    @NotBlank
    @Email(message = "Invalid email id")
    private String username;
}
