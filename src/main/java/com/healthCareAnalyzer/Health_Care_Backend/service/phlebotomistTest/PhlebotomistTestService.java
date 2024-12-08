package com.healthCareAnalyzer.Health_Care_Backend.service.phlebotomistTest;

import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.CreatePhlebotomistTestRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.phlebotomist.SaveLabTestRecordsDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.LabTestsEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistTestEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.LabTestsRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.PhlebotomistTestRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PhlebotomistTestService {
    private final AppointmentRepository appointmentRepository;
    private final LabTestsRepository labTestsRepository;
    private final PhlebotomistTestRepository phlebotomistTestRepository;

    public PhlebotomistTestService(AppointmentRepository appointmentRepository, PhlebotomistTestRepository phlebotomistTestRepository, LabTestsRepository labTestsRepository) {
        this.appointmentRepository = appointmentRepository;
        this.labTestsRepository = labTestsRepository;
        this.phlebotomistTestRepository = phlebotomistTestRepository;
    }

    public ResponseEntity<?> createPhlebotomistTestRecord(@Valid CreatePhlebotomistTestRecordDto createPhlebotomistTestRecordDto) {

        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(createPhlebotomistTestRecordDto.getAppointmentId());
        if (appointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        PhlebotomistTestEntity phlebotomistTestEntity = new PhlebotomistTestEntity();
        List<LabTestsEntity> labTestsEntityList = labTestsRepository.findAllById(createPhlebotomistTestRecordDto.getLabTestIds());
        phlebotomistTestEntity.setAppointmentEntity(appointmentEntity.get());
        phlebotomistTestEntity.setLabTestsEntities(labTestsEntityList);

        phlebotomistTestRepository.save(phlebotomistTestEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Phlebotomist test record created");
    }

    public ResponseEntity<?> getAllPendingTests() {
        return ResponseEntity.status(HttpStatus.OK).body(phlebotomistTestRepository.findByAppointmentEntity_Stage("phlebotomist"));
    }

    public ResponseEntity<?> saveLabTestRecords(@Valid SaveLabTestRecordsDto saveLabTestRecordsDto) {

        Optional<PhlebotomistTestEntity> optionalPhlebotomistTestEntity = phlebotomistTestRepository.findById(saveLabTestRecordsDto.getPhlebotomistTestId());
        if (optionalPhlebotomistTestEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phlebotomist test not found");
        }
        PhlebotomistTestEntity phlebotomistTestEntity = optionalPhlebotomistTestEntity.get();
        phlebotomistTestEntity.setPatientTestData(saveLabTestRecordsDto.getLabTestRecords());
        phlebotomistTestRepository.save(phlebotomistTestEntity);

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(saveLabTestRecordsDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }
        AppointmentEntity appointmentEntity = optionalAppointmentEntity.get();
        appointmentEntity.setStage("doctor_v2");
        appointmentRepository.save(appointmentEntity);

        return ResponseEntity.ok().body("Phlebotomist test record saved successfully");
    }
}