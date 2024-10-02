package com.healthCareAnalyzer.Health_Care_Backend.controller;

import com.healthCareAnalyzer.Health_Care_Backend.dto.RegisterRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.RegisterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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


    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.info(bindingResult.getAllErrors().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields are not valid");

            }

            String response = registerService.addUser(registerRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

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
