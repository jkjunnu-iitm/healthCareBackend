package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.LabTestsEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestsRepository extends JpaRepository<LabTestsEntity, Long> {
    List<LabTestsEntity> findByLabTestIdIn(@NotEmpty List<Long> labTestIds);
}
