package com.healthCareAnalyzer.Health_Care_Backend.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameRoleDto {
    @NotBlank
    @Email(message = "Invalid email id")
    private String username;
    @NotBlank
    @Pattern(regexp = "^ROLE_.*")
    private String role;
}
