package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.DoctorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DoctorEntity a WHERE a.userEntity.username=?1 and a.userEntity.isEnabled=false")
    int deleteDisabledUser(String username);

    @Query("SELECT new DoctorEntity (d.doctorId, d.userEntity) FROM DoctorEntity d WHERE d.userEntity.isEnabled=true")
    List<DoctorEntity> findAllDoctors();

}
