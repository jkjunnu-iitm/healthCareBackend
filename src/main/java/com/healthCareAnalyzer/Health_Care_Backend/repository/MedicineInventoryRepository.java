package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.MedicineInventoryEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventoryEntity, Long> {
    List<MedicineInventoryEntity> findByMedicineIdIn(@NotEmpty List<Long> medicineIds);
}
