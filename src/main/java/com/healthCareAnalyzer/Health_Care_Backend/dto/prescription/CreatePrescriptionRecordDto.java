package com.healthCareAnalyzer.Health_Care_Backend.dto.prescription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrescriptionRecordDto {
    @NotNull
    private Long appointmentId;
    @NotBlank
    private List<Long> medicineIds;
}
