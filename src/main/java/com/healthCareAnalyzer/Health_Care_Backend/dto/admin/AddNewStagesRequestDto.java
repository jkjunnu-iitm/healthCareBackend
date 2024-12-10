package com.healthCareAnalyzer.Health_Care_Backend.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewStagesRequestDto {
    @NotEmpty
    private List<String> stageList;
}
