package com.healthCareAnalyzer.Health_Care_Backend.service.appointment;

import com.healthCareAnalyzer.Health_Care_Backend.dto.doctor.UpdateStageRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.prescription.UpdateStageByAppointmentIdDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PatientEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.StageEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.StageRepository;
import com.healthCareAnalyzer.Health_Care_Backend.utility.ExtractUsernameFromToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final ExtractUsernameFromToken extractUsernameFromToken;
    private final AppointmentRepository appointmentRepository;
    private final StageRepository stageRepository;

    public AppointmentService(ExtractUsernameFromToken extractUsernameFromToken, AppointmentRepository appointmentRepository, StageRepository stageRepository) {
        this.extractUsernameFromToken = extractUsernameFromToken;
        this.appointmentRepository = appointmentRepository;
        this.stageRepository = stageRepository;
    }


    public Boolean validateStageName(String username, String stageName, Long appointmentId) {

        List<StageEntity> stageEntityList = stageRepository.findAll();
        List<String> defaultStageList = new ArrayList<>();

        for (StageEntity stageEntity : stageEntityList) {
            defaultStageList.add(stageEntity.getStageName());
        }

        if (!defaultStageList.contains(stageName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid stage name");
        }

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findByAppointmentIdAndDoctor_UserEntity_UsernameAndStageNotIn(appointmentId, username, Arrays.asList("completed", "rejected"));

        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid appointment id or Appointment is already in Completed/Rejected Stage");
        }

        String currentStageName = optionalAppointmentEntity.get().getStage();

        if (currentStageName.equals("approval") && stageName.equals("rejected")) {
            return true;
        }

        if (currentStageName.equals("doctor_v1") && stageName.equals("prescription")) {
            return true;
        }

        return defaultStageList.indexOf(currentStageName) + 1 == defaultStageList.indexOf(stageName);

    }


    public ResponseEntity<?> getAllAppointmentsWaitingForApproval(HttpServletRequest request) {

        String username = extractUsernameFromToken.extractUsernameFromToken(request);

        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAppointmentsWaitingForApproval(username);

        return getAppointmentEntityList(appointmentEntityList);

    }

    @NotNull
    private ResponseEntity<?> getAppointmentEntityList(List<AppointmentEntity> appointmentEntityList) {
        for (AppointmentEntity appointmentEntity : appointmentEntityList) {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(appointmentEntity.getPatient().getUserEntity().getFirstName());
            userEntity.setLastName(appointmentEntity.getPatient().getUserEntity().getLastName());

            PatientEntity patientEntity = appointmentEntity.getPatient();
            patientEntity.setUserEntity(userEntity);

            appointmentEntity.setPatient(patientEntity);
        }

        return ResponseEntity.ok(appointmentEntityList);
    }

    public ResponseEntity<?> updateStage(@Valid UpdateStageRequestDto updateStageRequestDto, HttpServletRequest request) {

        String username = extractUsernameFromToken.extractUsernameFromToken(request);

        if (!validateStageName(username, updateStageRequestDto.getStageName(), updateStageRequestDto.getAppointmentId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid stage name");
        }

        int a = appointmentRepository.updateStageByIdAndDoctor(updateStageRequestDto.getAppointmentId(), username, updateStageRequestDto.getStageName());

        if (a == 1) {
            return ResponseEntity.ok("Successfully updated");
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update stage");

    }

    public ResponseEntity<?> getAllUpcomingAppointments(HttpServletRequest request) {
        String username = extractUsernameFromToken.extractUsernameFromToken(request);

        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAllUpcomingAppointments(username);
        return getAppointmentEntityList(appointmentEntityList);

    }


    public ResponseEntity<?> getUpcomingAppointmentById(Long appointmentId, HttpServletRequest request) {
        String username = extractUsernameFromToken.extractUsernameFromToken(request);
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findByAppointmentIdAndDoctor_UserEntity_Username(appointmentId, username);
        return getAppointmentEntityList(appointmentEntityList);
    }

    public ResponseEntity<?> updateStageToCompletedByAppointmentId(@Valid UpdateStageByAppointmentIdDto updateStageByAppointmentIdDto) {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(updateStageByAppointmentIdDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid appointment id");
        }
        AppointmentEntity appointmentEntity = optionalAppointmentEntity.get();
        appointmentEntity.setStage("completed");
        appointmentRepository.save(appointmentEntity);
        return ResponseEntity.ok("Successfully updated");
    }

    public ResponseEntity<?> getAppointmentDetailsByPatientId(HttpServletRequest httpServletRequest) {
        String username = extractUsernameFromToken.extractUsernameFromToken(httpServletRequest);
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findByPatient_UserEntity_Username(username);
        return ResponseEntity.ok(appointmentEntityList);
    }
}
