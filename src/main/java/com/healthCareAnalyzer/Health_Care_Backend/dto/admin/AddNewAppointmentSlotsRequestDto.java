package com.healthCareAnalyzer.Health_Care_Backend.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewAppointmentSlotsRequestDto {
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
}
