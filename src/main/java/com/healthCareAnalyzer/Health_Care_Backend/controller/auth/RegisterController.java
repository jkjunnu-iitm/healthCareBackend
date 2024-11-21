package com.healthCareAnalyzer.Health_Care_Backend.controller.auth;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.register.RegisterRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.register.RegisterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) throws Exception {
        return registerService.registerUser(registerRequestDto);
    }

}
