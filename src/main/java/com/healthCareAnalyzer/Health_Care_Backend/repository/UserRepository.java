package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new UserEntity (u.username,u.firstName,u.lastName,u.role) from UserEntity u where u.isEnabled=false")
    List<UserEntity> findDisabledUsers();

    @Query("SELECT new UserEntity (u.username,u.firstName,u.lastName,u.role) from UserEntity u where u.username=?1")
    Optional<UserEntity> findUser(String username);
}
