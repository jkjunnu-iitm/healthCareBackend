package com.healthCareAnalyzer.Health_Care_Backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDashboardPasswordDto {

    @NotBlank
    @Size(min = 4, max = 20, message = "Min password length 4")
    private String oldPassword;
    @NotBlank
    @Size(min = 4, max = 20, message = "Min password length 4")
    private String newPassword;
}
