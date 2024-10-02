package com.healthCareAnalyzer.Health_Care_Backend.dto;

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
    @Email(message = "Invalid email id")
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Size(min = 4,max = 20, message = "Min password length 6")
    private String password;
    @Size(min = 4,max = 20, message = "Min password length 6")
    private String confirmPassword;
    @Pattern(regexp = "^ROLE_.*")
    private String roles;
}
