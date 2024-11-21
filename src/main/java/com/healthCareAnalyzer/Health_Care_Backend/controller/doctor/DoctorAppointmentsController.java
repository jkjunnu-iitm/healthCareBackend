package com.healthCareAnalyzer.Health_Care_Backend.controller.doctor;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.service.appointment.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor/appointment")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
@Slf4j
public class DoctorAppointmentsController {

    private final AppointmentService appointmentService;

    public DoctorAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllAppointmentsWaitingForApproval")
    public ResponseEntity<?> getAllAppointmentsWaitingForApproval(HttpServletRequest request) {
        return appointmentService.getAllAppointmentsWaitingForApproval(request);
    }

}
