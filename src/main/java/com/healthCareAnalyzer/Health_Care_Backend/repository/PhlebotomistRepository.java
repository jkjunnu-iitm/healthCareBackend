package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.PhlebotomistEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhlebotomistRepository extends JpaRepository<PhlebotomistEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PhlebotomistEntity a WHERE a.userEntity.username=?1 and a.userEntity.isEnabled=false")
    int deleteDisabledUser(String username);

}
