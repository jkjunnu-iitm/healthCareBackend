package com.healthCareAnalyzer.Health_Care_Backend.controller.auth;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login.LoginRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.login.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequestDto loginRequestDto) {

        return loginService.authenticateUser(loginRequestDto.getUsername(), loginRequestDto.getPassword());

    }
}
