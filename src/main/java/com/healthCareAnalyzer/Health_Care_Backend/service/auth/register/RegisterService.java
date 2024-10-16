package com.healthCareAnalyzer.Health_Care_Backend.service.auth.register;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.register.RegisterRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.*;
import com.healthCareAnalyzer.Health_Care_Backend.repository.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class RegisterService {


    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PhlebotomistRepository phlebotomistRepository;
    private final ReceptionistRepository receptionistRepository;

    @Autowired
    public RegisterService(PasswordEncoder passwordEncoder, AdminRepository adminRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, PhlebotomistRepository phlebotomistRepository, ReceptionistRepository receptionistRepository) {

        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.phlebotomistRepository = phlebotomistRepository;
        this.receptionistRepository = receptionistRepository;

    }

    public Boolean addUser(@Valid RegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        userEntity.setUsername(registerRequestDto.getUsername());
        userEntity.setRole(registerRequestDto.getRole());
        userEntity.setFirstName(registerRequestDto.getFirstName());
        userEntity.setLastName(registerRequestDto.getLastName());
        userEntity.setIsEnabled(registerRequestDto.getRole().equals("ROLE_PATIENT"));

        switch (registerRequestDto.getRole()) {
            case "ROLE_ADMIN" -> {
                AdminEntity adminEntity = new AdminEntity();
                adminEntity.setUserEntity(userEntity);
                adminEntity.setDashboardPassword(passwordEncoder.encode("1234"));
                adminRepository.save(adminEntity);
                return true;
            }
            case "ROLE_DOCTOR" -> {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setUserEntity(userEntity);
                doctorRepository.save(doctorEntity);
                return true;
            }
            case "ROLE_PATIENT" -> {
                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setUserEntity(userEntity);
                patientRepository.save(patientEntity);
                return true;
            }
            case "ROLE_PHLEBOTOMIST" -> {
                PhlebotomistEntity phlebotomistEntity = new PhlebotomistEntity();
                phlebotomistEntity.setUserEntity(userEntity);
                phlebotomistRepository.save(phlebotomistEntity);
                return true;
            }
            case "ROLE_RECEPTIONIST" -> {
                ReceptionistEntity receptionistEntity = new ReceptionistEntity();
                receptionistEntity.setUserEntity(userEntity);
                receptionistRepository.save(receptionistEntity);
                return true;
            }
        }

        return false;

    }


}
