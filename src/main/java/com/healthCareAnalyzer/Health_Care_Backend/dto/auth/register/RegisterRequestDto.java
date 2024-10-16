package com.healthCareAnalyzer.Health_Care_Backend.dto.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequestDto {
    @NotBlank
    @Email(message = "Invalid email id")
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Size(min = 4, max = 20, message = "Min password length 4")
    private String password;
    @NotBlank
    @Size(min = 4, max = 20, message = "Min password length 4")
    private String confirmPassword;
    @NotBlank
    @Pattern(regexp = "^ROLE_.*")
    private String role;
}
