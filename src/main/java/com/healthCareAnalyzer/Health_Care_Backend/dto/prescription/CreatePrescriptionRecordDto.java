package com.healthCareAnalyzer.Health_Care_Backend.dto.prescription;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrescriptionRecordDto {
    @NotEmpty
    private Long appointmentId;
    @NotEmpty
    private List<Long> medicineIds;
}
