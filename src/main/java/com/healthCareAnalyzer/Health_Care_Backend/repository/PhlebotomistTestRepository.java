package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhlebotomistTestRepository extends JpaRepository<PhlebotomistTestEntity, Long> {
    Object findByAppointmentEntity_Stage(String phlebotomist);
}
