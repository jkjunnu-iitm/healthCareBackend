package com.healthCareAnalyzer.Health_Care_Backend.controller.admin;

import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.UpdateDashboardPasswordRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.UsernameDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.UsernameRoleDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Slf4j
public class AdminController {


    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/updateDashboardPassword")
    public ResponseEntity<?> updateDashboardPassword(@RequestBody @Valid UpdateDashboardPasswordRequestDto updateDashboardPasswordRequestDto, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        try {

            if (bindingResult.hasErrors()) {
                log.info(bindingResult.getAllErrors().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields are not valid");
            }

            return adminService.updateDashboardPassword(updateDashboardPasswordRequestDto.getOldPassword(), updateDashboardPasswordRequestDto.getNewPassword(), updateDashboardPasswordRequestDto.getRetypeNewPassword(), httpServletRequest);


        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/fetchDisabledUsers")
    public ResponseEntity<?> fetchDisabledUsers() {
        try {

            return adminService.fetchDisabledUsers();

        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/enableDisabledUser")
    public ResponseEntity<?> enableDisabledUser(@Valid @RequestBody UsernameDto usernameDto, BindingResult bindingResult) {
        try {

            if (!bindingResult.hasErrors()) {

                return adminService.enableDisabledUser(usernameDto.getUsername());

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JSON format error");
            }


        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteDisabledUser")
    public ResponseEntity<?> deleteDisabledUser(@Valid @RequestBody UsernameRoleDto usernameRoleDto, BindingResult bindingResult) {
        try {

            if (!bindingResult.hasErrors()) {

                return adminService.deleteDisabledUser(usernameRoleDto.getUsername(), usernameRoleDto.getRole());

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JSON format error");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
