package com.healthCareAnalyzer.Health_Care_Backend.dto.prescription;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageByAppointmentIdDto {
    @NotBlank
    private Long appointmentId;
}
