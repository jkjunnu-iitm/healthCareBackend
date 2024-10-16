package com.healthCareAnalyzer.Health_Care_Backend.controller.auth;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.register.RegisterRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.register.RegisterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                Boolean isUserAdded = registerService.addUser(registerRequestDto);
                if (isUserAdded) {

                    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User could not be registered");
                }

            } else {
                log.info(bindingResult.getAllErrors().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields are not valid");
            }

        } catch (ResponseStatusException e) {

            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
