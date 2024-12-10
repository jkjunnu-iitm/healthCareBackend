package com.healthCareAnalyzer.Health_Care_Backend.dto.prescription;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStageByAppointmentIdDto {
    @NotEmpty
    private Long appointmentId;
}
