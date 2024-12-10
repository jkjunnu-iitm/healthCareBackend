package com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveLabTestRecordsDto {
    @NotEmpty
    private Long phlebotomistTestId;
    @NotEmpty
    private Long appointmentId;
    @NotBlank
    private String labTestRecords;
}
