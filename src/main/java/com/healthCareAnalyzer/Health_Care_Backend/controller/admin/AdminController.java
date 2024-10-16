package com.healthCareAnalyzer.Health_Care_Backend.controller.admin;

import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.UpdateDashboardPasswordRequestDto;
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

            String serviceResponse = adminService.updateDashboardPassword(updateDashboardPasswordRequestDto.getOldPassword(), updateDashboardPasswordRequestDto.getNewPassword(), httpServletRequest);
            return ResponseEntity.ok(serviceResponse);

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


}
