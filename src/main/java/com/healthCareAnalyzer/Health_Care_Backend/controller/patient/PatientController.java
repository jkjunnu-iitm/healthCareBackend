package com.healthCareAnalyzer.Health_Care_Backend.controller.patient;

import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.BookAppointmentRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetAllDoctorsResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetOpenSlotsRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.patient.PatientAppointmentBookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
@Slf4j
public class PatientController {

    private final PatientAppointmentBookingService patientAppointmentBookingService;

    public PatientController(PatientAppointmentBookingService patientAppointmentBookingService) {
        this.patientAppointmentBookingService = patientAppointmentBookingService;
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<?> getAllDoctors() {

        List<GetAllDoctorsResponseDto> doctorsDtoList = patientAppointmentBookingService.getAllDoctors();
        return ResponseEntity.status(HttpStatus.OK).body(doctorsDtoList);

    }

    @PostMapping("/getOpenSlots")
    public ResponseEntity<?> getOpenSlots(@Valid @RequestBody GetOpenSlotsRequestDto openSlotsRequestDto) {

        return patientAppointmentBookingService.getOpenSlots(openSlotsRequestDto);

    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody BookAppointmentRequestDto bookAppointmentRequestDto, HttpServletRequest httpServletRequest) {

        return patientAppointmentBookingService.bookAppointment(bookAppointmentRequestDto, httpServletRequest);

    }
}


