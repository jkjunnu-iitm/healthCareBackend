package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AdminEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE AdminEntity a SET a.dashboardPassword=?2 WHERE a.userEntity.username=?1")
    int updateDashboardPassword(String username, @NotBlank @Size(min = 4, max = 20, message = "Min password length 4") String newPassword);

    @Query("SELECT a.dashboardPassword FROM AdminEntity a WHERE a.userEntity.username=?1")
    String findDashboardPassword(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM AdminEntity a WHERE a.userEntity.username=?1 and a.userEntity.isEnabled=false")
    int deleteDisabledUser(String username);
}
