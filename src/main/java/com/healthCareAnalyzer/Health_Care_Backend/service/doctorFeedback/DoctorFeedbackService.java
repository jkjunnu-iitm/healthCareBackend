package com.healthCareAnalyzer.Health_Care_Backend.service.doctorFeedback;

import com.healthCareAnalyzer.Health_Care_Backend.dto.doctorFeedback.CreateDoctorFeedbackRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.DoctorFeedbackEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.DoctorFeedbackRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class DoctorFeedbackService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorFeedbackRepository doctorFeedbackRepository;

    public DoctorFeedbackService(AppointmentRepository appointmentRepository, DoctorFeedbackRepository doctorFeedbackRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorFeedbackRepository = doctorFeedbackRepository;
    }

    public ResponseEntity<?> createDoctorFeedbackRecord(@Valid CreateDoctorFeedbackRecordDto createDoctorFeedbackRecordDto) {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(createDoctorFeedbackRecordDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        DoctorFeedbackEntity doctorFeedbackEntity = new DoctorFeedbackEntity();
        doctorFeedbackEntity.setFeedback(createDoctorFeedbackRecordDto.getDoctorFeedback());
        doctorFeedbackEntity.setAppointmentEntity(optionalAppointmentEntity.get());

        doctorFeedbackRepository.save(doctorFeedbackEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created doctor feedback");
    }
}
