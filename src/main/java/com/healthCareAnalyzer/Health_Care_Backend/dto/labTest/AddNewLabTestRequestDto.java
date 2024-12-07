package com.healthCareAnalyzer.Health_Care_Backend.dto.labTest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewLabTestRequestDto {

    @NotNull
    private String labTestName;
    @NotNull
    private String[] labTestFields;

}
