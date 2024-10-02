package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String username);
}
