package com.healthCareAnalyzer.Health_Care_Backend.service.admin;

import com.healthCareAnalyzer.Health_Care_Backend.config.ExtractUsernameFromToken;
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
import org.springframework.web.server.ResponseStatusException;


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
    private final ExtractUsernameFromToken extractUsernameFromToken;

    @Autowired
    public AdminService(AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, DoctorRepository doctorRepository, PhlebotomistRepository phlebotomistRepository, ReceptionistRepository receptionistRepository, AppointmentSlotRepository appointmentSlotRepository, ExtractUsernameFromToken extractUsernameFromToken) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.phlebotomistRepository = phlebotomistRepository;
        this.receptionistRepository = receptionistRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
        this.extractUsernameFromToken = extractUsernameFromToken;
    }


    public ResponseEntity<?> updateDashboardPassword(String oldPassword, String newPassword, String retypeNewPassword, HttpServletRequest request) {

        if (!newPassword.equals(retypeNewPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Passwords dont match");
        }

        if (oldPassword.equals(newPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be the same as old password");
        }

        String username = extractUsernameFromToken.extractUsernameFromToken(request);
        String currentPassword = adminRepository.findDashboardPassword(username);
        if (passwordEncoder.matches(oldPassword, currentPassword)) {

            int result = adminRepository.updateDashboardPassword(username, passwordEncoder.encode(newPassword));

            if (result == 1) {
                return ResponseEntity.ok().body("Successfully set dashboard password");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Update could not take place");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password does not match");
        }

    }

    public ResponseEntity<?> fetchDisabledUsers() {
        List<UserEntity> disabledUsers = userRepository.findDisabledUsers();
        if (!disabledUsers.isEmpty()) {
            return ResponseEntity.ok().body(disabledUsers);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No disabled users found");
        }
    }


    public ResponseEntity<?> enableDisabledUser(String username) {

        int result = userRepository.enableDisabledUser(username);

        if (result == 1) {
            return ResponseEntity.ok().body("Successfully enabled " + username);
        } else if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nothing is updated, inappropriate input");
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fatal error: More than 1 record updated");
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
            return ResponseEntity.ok().body("Successfully deleted " + username);
        } else if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nothing is deleted, inappropriate input");
        } else if (result > 1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fatal error: More than 1 record deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exist: " + role);
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
