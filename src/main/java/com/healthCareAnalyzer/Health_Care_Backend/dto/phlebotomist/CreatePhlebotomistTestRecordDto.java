package com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePhlebotomistTestRecordDto {
    @NotEmpty
    private Long appointmentId;
    @NotEmpty
    private List<Long> labTestIds;
}
