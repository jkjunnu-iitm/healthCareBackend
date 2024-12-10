package com.healthCareAnalyzer.Health_Care_Backend.dto.prescription;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageByAppointmentIdDto {
    @NotNull
    private Long appointmentId;
}
