package com.healthCareAnalyzer.Health_Care_Backend.dto.doctor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageRequestDto {
    @NotEmpty
    private Long appointmentId;
    @NotBlank
    private String stageName;
}
