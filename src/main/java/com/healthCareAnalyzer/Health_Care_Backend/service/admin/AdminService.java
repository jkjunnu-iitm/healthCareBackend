package com.healthCareAnalyzer.Health_Care_Backend.service.admin;

import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.AddNewAppointmentSlotsRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.*;
import com.healthCareAnalyzer.Health_Care_Backend.repository.*;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PhlebotomistRepository phlebotomistRepository;
    private final ReceptionistRepository receptionistRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, DoctorRepository doctorRepository, PhlebotomistRepository phlebotomistRepository, ReceptionistRepository receptionistRepository, AppointmentSlotRepository appointmentSlotRepository) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.phlebotomistRepository = phlebotomistRepository;
        this.receptionistRepository = receptionistRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }


    public ResponseEntity<?> updateDashboardPassword(String oldPassword, String newPassword, String retypeNewPassword, HttpServletRequest request) {

        String authHeader;
        authHeader = request.getHeader("Authorization");

        if (!newPassword.equals(retypeNewPassword)) {
            return new ResponseEntity<>("Passwords dont match", HttpStatus.UNAUTHORIZED);
        }

        if (oldPassword.equals(newPassword)) {
            return new ResponseEntity<>("New password cannot be the same as old password", HttpStatus.BAD_REQUEST);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            String currentPassword = adminRepository.findDashboardPassword(username);
            if (passwordEncoder.matches(oldPassword, currentPassword)) {

                int result = adminRepository.updateDashboardPassword(username, passwordEncoder.encode(newPassword));

                if (result == 1) {
                    return new ResponseEntity<>("Successfully set dashboard password", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Update could not take place", HttpStatus.UNAUTHORIZED);
                }

            } else {
                return new ResponseEntity<>("Old password does not match", HttpStatus.UNAUTHORIZED);
            }

        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);

    }

    public ResponseEntity<?> fetchDisabledUsers() {
        List<UserEntity> disabledUsers = userRepository.findDisabledUsers();
        if (!disabledUsers.isEmpty()) {
            return new ResponseEntity<>(disabledUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> enableDisabledUser(String username) {

        int result = userRepository.enableDisabledUser(username);

        if (result == 1) {
            return new ResponseEntity<>("Successfully enabled " + username, HttpStatus.OK);
        } else if (result == 0) {
            return new ResponseEntity<>("Nothing is updated, inappropriate input", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Fatal error: More than 1 record updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> deleteDisabledUser(String username, String role) {

        int result = -1;

        switch (role) {
            case "ROLE_ADMIN" -> result = adminRepository.deleteDisabledUser(username);
            case "ROLE_DOCTOR" -> result = doctorRepository.deleteDisabledUser(username);
            case "ROLE_PHLEBOTOMIST" -> result = phlebotomistRepository.deleteDisabledUser(username);
            case "ROLE_RECEPTIONIST" -> result = receptionistRepository.deleteDisabledUser(username);
        }

        if (result == 1) {
            userRepository.deleteById(username);
            return new ResponseEntity<>("Successfully deleted " + username, HttpStatus.OK);
        } else if (result == 0) {
            return new ResponseEntity<>("Nothing is deleted, inappropriate input", HttpStatus.BAD_REQUEST);
        } else if (result > 1) {
            return new ResponseEntity<>("Fatal error: More than 1 record deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Role does not exist: " + role, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public void addNewSlots(@Valid List<AddNewAppointmentSlotsRequestDto> addNewAppointmentSlotsRequestDto) {
        for (AddNewAppointmentSlotsRequestDto addNewAppointmentSlots : addNewAppointmentSlotsRequestDto) {
            AppointmentSlotEntity appointmentSlotEntity = new AppointmentSlotEntity();
            appointmentSlotEntity.setStartTime(addNewAppointmentSlots.getStartTime());
            appointmentSlotEntity.setEndTime(addNewAppointmentSlots.getEndTime());
            appointmentSlotRepository.save(appointmentSlotEntity);
        }
    }
}
