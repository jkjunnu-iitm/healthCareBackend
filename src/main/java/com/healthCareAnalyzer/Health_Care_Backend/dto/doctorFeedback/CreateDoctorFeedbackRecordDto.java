package com.healthCareAnalyzer.Health_Care_Backend.dto.doctorFeedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorFeedbackRecordDto {
    @NotEmpty
    private Long appointmentId;
    @NotBlank
    private String doctorFeedback;
}
