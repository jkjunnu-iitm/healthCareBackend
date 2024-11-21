package com.healthCareAnalyzer.Health_Care_Backend.service.appointment;

import com.healthCareAnalyzer.Health_Care_Backend.config.ExtractUsernameFromToken;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final ExtractUsernameFromToken extractUsernameFromToken;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(ExtractUsernameFromToken extractUsernameFromToken, AppointmentRepository appointmentRepository) {
        this.extractUsernameFromToken = extractUsernameFromToken;
        this.appointmentRepository = appointmentRepository;
    }


    public ResponseEntity<?> getAllAppointmentsWaitingForApproval(HttpServletRequest request) {

        String username = extractUsernameFromToken.extractUsernameFromToken(request);

        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAppointmentsWaitingForApproval(username);

        return ResponseEntity.ok(appointmentEntityList);

    }
}
