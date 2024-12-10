package com.healthCareAnalyzer.Health_Care_Backend.dto.doctor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageRequestDto {
    @NotNull
    private Long appointmentId;
    @NotBlank
    private String stageName;
}
