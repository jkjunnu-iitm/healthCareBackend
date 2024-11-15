package com.healthCareAnalyzer.Health_Care_Backend.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllDoctorsResponseDto {

    private Long doctorId;
    private String firstName;
    private String lastName;

}
