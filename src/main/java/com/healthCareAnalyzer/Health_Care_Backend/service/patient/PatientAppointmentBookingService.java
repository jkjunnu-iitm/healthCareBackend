package com.healthCareAnalyzer.Health_Care_Backend.service.patient;

import com.healthCareAnalyzer.Health_Care_Backend.config.ExtractUsernameFromToken;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.BookAppointmentRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetAllDoctorsResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.patient.GetOpenSlotsRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentSlotEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.DoctorEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PatientEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentSlotRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.DoctorRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.PatientRepository;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientAppointmentBookingService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;
    private final PatientRepository patientRepository;
    private final ExtractUsernameFromToken extractUsernameFromToken;

    public PatientAppointmentBookingService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, AppointmentSlotRepository appointmentSlotRepository, PatientRepository patientRepository, ExtractUsernameFromToken extractUsernameFromToken) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
        this.patientRepository = patientRepository;
        this.extractUsernameFromToken = extractUsernameFromToken;
    }

    public List<GetAllDoctorsResponseDto> getAllDoctors() {
        List<DoctorEntity> doctorEntityList = doctorRepository.findAllDoctors();

        List<GetAllDoctorsResponseDto> doctorsDtoList = new ArrayList<>();

        for (DoctorEntity doctorEntity : doctorEntityList) {
            doctorsDtoList.add(new GetAllDoctorsResponseDto(doctorEntity.getDoctorId(), doctorEntity.getUserEntity().getFirstName(), doctorEntity.getUserEntity().getLastName()));
        }

        return doctorsDtoList;
    }

    public ResponseEntity<?> getOpenSlots(@Valid GetOpenSlotsRequestDto openSlotsRequestDto) {

        List<Long> bookedSlotIds = appointmentRepository.findOpenSlots(openSlotsRequestDto.getDoctorId(), openSlotsRequestDto.getDateOfAppointment());
        List<AppointmentSlotEntity> appointmentSlotEntityList = appointmentSlotRepository.findAll();

        for (Long slotId : bookedSlotIds) {
            appointmentSlotEntityList.removeIf(appointmentSlotEntity -> slotId.equals(appointmentSlotEntity.getSlotId()));
        }
        return ResponseEntity.ok(appointmentSlotEntityList);
    }

    public ResponseEntity<?> bookAppointment(@Valid BookAppointmentRequestDto bookAppointmentRequestDto, HttpServletRequest httpServletRequest) {

        Optional<DoctorEntity> doctor = doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());

        if (doctor.isEmpty()) {
            throw new UsernameNotFoundException("Doctor not found");
        }

        Optional<AppointmentSlotEntity> appointmentSlotEntity = appointmentSlotRepository.findById(bookAppointmentRequestDto.getSlotId());

        if (appointmentSlotEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slot not found");
        }

        String username = extractUsernameFromToken.extractUsernameFromToken(httpServletRequest);

        PatientEntity patientEntity = patientRepository.findByUserEntity_Username(username);

        List<String> stageHistoryList = appointmentRepository.findStageByPatientId(patientEntity.getPatientId());

        if (!stageHistoryList.isEmpty()) {
            for (String stage : stageHistoryList) {
                if (!Objects.equals(stage, "completed")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot book an appointment, another appointment is ongoing");
                }
            }
        }

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctor(doctor.get());
        appointmentEntity.setDateOfAppointment(bookAppointmentRequestDto.getDateOfAppointment());
        appointmentEntity.setDateOfBooking(LocalDateTime.now());
        appointmentEntity.setStage("approval");
        appointmentEntity.setSlot(appointmentSlotEntity.get());

        appointmentRepository.save(appointmentEntity);

        return ResponseEntity.ok().body("Appointment booked successfully");


    }
}
