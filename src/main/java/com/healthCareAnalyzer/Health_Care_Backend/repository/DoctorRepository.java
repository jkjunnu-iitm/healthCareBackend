package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.DoctorEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DoctorEntity a WHERE a.userEntity.username=?1 and a.userEntity.isEnabled=false")
    int deleteDisabledUser(String username);
}
