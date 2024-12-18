package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.ReceptionistEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepository extends JpaRepository<ReceptionistEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ReceptionistEntity a WHERE a.userEntity.username=?1 and a.userEntity.isEnabled=false")
    int deleteDisabledUser(String username);

}
