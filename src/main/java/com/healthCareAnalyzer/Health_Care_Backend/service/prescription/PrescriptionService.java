package com.healthCareAnalyzer.Health_Care_Backend.service.prescription;

import com.healthCareAnalyzer.Health_Care_Backend.dto.prescription.CreatePrescriptionRecordDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.MedicineInventoryEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.PrescriptionEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AppointmentRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.MedicineInventoryRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.PrescriptionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    private final AppointmentRepository appointmentRepository;
    private final MedicineInventoryRepository medicineInventoryRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(AppointmentRepository appointmentRepository, MedicineInventoryRepository medicineInventoryRepository, PrescriptionRepository prescriptionRepository) {
        this.appointmentRepository = appointmentRepository;
        this.medicineInventoryRepository = medicineInventoryRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public ResponseEntity<?> createPrescriptionRecord(@Valid CreatePrescriptionRecordDto createPrescriptionRecordDto) {

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(createPrescriptionRecordDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        List<MedicineInventoryEntity> medicineInventoryEntityList = medicineInventoryRepository.findAllById(createPrescriptionRecordDto.getMedicineIds());

        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setAppointmentEntity(optionalAppointmentEntity.get());
        prescriptionEntity.setMedicineInventoryEntity(medicineInventoryEntityList);

        prescriptionRepository.save(prescriptionEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created prescription record");

    }

    public ResponseEntity<?> getAllPendingPrescriptions() {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionRepository.findByAppointmentEntity_Stage("receptionist"));
    }


}
