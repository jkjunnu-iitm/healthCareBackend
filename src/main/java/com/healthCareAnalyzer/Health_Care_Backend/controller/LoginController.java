package com.healthCareAnalyzer.Health_Care_Backend.controller;

import com.healthCareAnalyzer.Health_Care_Backend.dto.LoginRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {


    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    public LoginController(AuthenticationManager authenticationManager, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {

            LoginResponseDto loginResponseDto;
            loginResponseDto = loginService.findUserByUserName(loginRequestDto.getUserName());

            return ResponseEntity.ok(loginResponseDto);

        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }
}
