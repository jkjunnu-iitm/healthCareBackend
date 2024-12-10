package com.healthCareAnalyzer.Health_Care_Backend.dto.patient;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAppointmentRequestDto {
    @NotNull
    private Long doctorId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate dateOfAppointment;
    @NotNull
    private Long slotId;
}
