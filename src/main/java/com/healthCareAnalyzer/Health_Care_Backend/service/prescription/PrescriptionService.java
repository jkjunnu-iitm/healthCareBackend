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
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineInventoryRepository medicineInventoryRepository;

    public PrescriptionService(AppointmentRepository appointmentRepository, PrescriptionRepository prescriptionRepository, MedicineInventoryRepository medicineInventoryRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicineInventoryRepository = medicineInventoryRepository;
    }

    public ResponseEntity<?> createPrescriptionRecord(@Valid CreatePrescriptionRecordDto createPrescriptionRecordDto) {

        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(createPrescriptionRecordDto.getAppointmentId());
        if (optionalAppointmentEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }

        List<MedicineInventoryEntity> medicineInventoryEntityList = medicineInventoryRepository.findByMedicineIdIn(createPrescriptionRecordDto.getMedicineIds());

        if (medicineInventoryEntityList.size() != createPrescriptionRecordDto.getMedicineIds().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medicine Ids do not exist");
        }

        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setAppointmentEntity(optionalAppointmentEntity.get());
        prescriptionEntity.setMedicineIds(createPrescriptionRecordDto.getMedicineIds().toArray(new Long[0]));

        prescriptionRepository.save(prescriptionEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created prescription record");

    }

    public ResponseEntity<?> getAllPendingPrescriptions() {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionRepository.findByAppointmentEntity_Stage("receptionist"));
    }


}
