package com.healthCareAnalyzer.Health_Care_Backend.controller.admin;

import com.healthCareAnalyzer.Health_Care_Backend.dto.admin.AddNewAppointmentSlotsRequestDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> updateDashboardPassword(@RequestBody @Valid UpdateDashboardPasswordRequestDto updateDashboardPasswordRequestDto, HttpServletRequest httpServletRequest) {

        return adminService.updateDashboardPassword(updateDashboardPasswordRequestDto.getOldPassword(), updateDashboardPasswordRequestDto.getNewPassword(), updateDashboardPasswordRequestDto.getRetypeNewPassword(), httpServletRequest);

    }

    @GetMapping("/fetchDisabledUsers")
    public ResponseEntity<?> fetchDisabledUsers() {

        return adminService.fetchDisabledUsers();

    }

    @PutMapping("/enableDisabledUser")
    public ResponseEntity<?> enableDisabledUser(@Valid @RequestBody UsernameDto usernameDto) {

        return adminService.enableDisabledUser(usernameDto.getUsername());

    }

    @DeleteMapping("/deleteDisabledUser")
    public ResponseEntity<?> deleteDisabledUser(@Valid @RequestBody UsernameRoleDto usernameRoleDto) {

        return adminService.deleteDisabledUser(usernameRoleDto.getUsername(), usernameRoleDto.getRole());


    }

    @PostMapping("/addNewSlots")
    public ResponseEntity<?> addNewSlots(@Valid @RequestBody List<AddNewAppointmentSlotsRequestDto> addNewAppointmentSlotsRequestDto) {

        adminService.addNewSlots(addNewAppointmentSlotsRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully added new slots");

    }


}
