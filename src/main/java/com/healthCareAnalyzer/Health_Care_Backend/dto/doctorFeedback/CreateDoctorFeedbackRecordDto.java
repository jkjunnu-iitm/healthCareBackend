package com.healthCareAnalyzer.Health_Care_Backend.dto.doctorFeedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorFeedbackRecordDto {
    @NotNull
    private Long appointmentId;
    @NotBlank
    private String doctorFeedback;
}
