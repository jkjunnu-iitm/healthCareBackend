package com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveLabTestRecordsDto {
    @NotBlank
    private Long phlebotomistTestId;
    @NotNull
    private Long appointmentId;
    @NotBlank
    private String labTestRecords;
}
