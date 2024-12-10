package com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePhlebotomistTestRecordDto {
    @NotNull
    private Long appointmentId;
    @NotEmpty
    private List<Long> labTestIds;
}
