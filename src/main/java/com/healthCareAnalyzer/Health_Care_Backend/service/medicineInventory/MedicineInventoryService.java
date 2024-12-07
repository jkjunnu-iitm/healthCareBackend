package com.healthCareAnalyzer.Health_Care_Backend.service.medicineInventory;

import com.healthCareAnalyzer.Health_Care_Backend.dto.medicineInventory.AddNewMedicineInventoryRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.MedicineInventoryEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.MedicineInventoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineInventoryService {


    private final MedicineInventoryRepository medicineInventoryRepository;

    public MedicineInventoryService(MedicineInventoryRepository medicineInventoryRepository) {
        this.medicineInventoryRepository = medicineInventoryRepository;
    }

    public ResponseEntity<?> addNewMedicines(@Valid List<AddNewMedicineInventoryRequestDto> addNewMedicineInventoryRequestDtoList) {

        List<MedicineInventoryEntity> medicineInventoryEntityList = new ArrayList<>();
        for (AddNewMedicineInventoryRequestDto addNewMedicineInventoryRequestDto : addNewMedicineInventoryRequestDtoList) {
            MedicineInventoryEntity medicineInventoryEntity = new MedicineInventoryEntity();
            medicineInventoryEntity.setMedicineName(addNewMedicineInventoryRequestDto.getMedicineName());
            medicineInventoryEntity.setMedicineQuantity(addNewMedicineInventoryRequestDto.getMedicineQuantity());
            medicineInventoryEntity.setMedicineSerialNumber(addNewMedicineInventoryRequestDto.getMedicineSerialNumber());
            medicineInventoryEntityList.add(medicineInventoryEntity);
        }
        medicineInventoryRepository.saveAll(medicineInventoryEntityList);

        return ResponseEntity.ok("Successfully added medicine inventory");

    }

    public ResponseEntity<?> getAllMedicineInventory() {
        return ResponseEntity.ok(medicineInventoryRepository.findAll());
    }
}
