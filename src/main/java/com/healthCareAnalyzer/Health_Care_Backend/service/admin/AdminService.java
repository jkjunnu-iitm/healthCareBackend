package com.healthCareAnalyzer.Health_Care_Backend.service.admin;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AdminEntity;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.AdminRepository;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserRepository;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public String updateDashboardPassword(@NotBlank @Size(min = 4, max = 20, message = "Min password length 4") String oldPassword, @NotBlank @Size(min = 4, max = 20, message = "Min password length 4") String newPassword, HttpServletRequest request) {

        String authHeader;
        authHeader = request.getHeader("Authorization");

        if (oldPassword.equals(newPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be the same as old password");
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            Optional<AdminEntity> adminEntityOptional = adminRepository.findByUserEntity(userEntity);
            if (adminEntityOptional.isPresent()) {
                AdminEntity adminEntity = adminEntityOptional.get();
                String currentPassword = adminEntity.getDashboardPassword();
                if (!passwordEncoder.matches(oldPassword, currentPassword)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
                } else {
                    adminEntity.setDashboardPassword(passwordEncoder.encode(newPassword));
                    adminRepository.save(adminEntity);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }

            return "Successfully set dashboard password";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }


    }

    public ResponseEntity<?> fetchDisabledUsers() {
        List<UserEntity> disabledUsers = userRepository.findDisabledUsers();
        if (!disabledUsers.isEmpty()) {
            return new ResponseEntity<>(disabledUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
