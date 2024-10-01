package com.healthCareAnalyzer.Health_Care_Backend.controller;

import com.healthCareAnalyzer.Health_Care_Backend.dto.AuthRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.AuthResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserInfo;
import com.healthCareAnalyzer.Health_Care_Backend.service.AuthService;
import com.healthCareAnalyzer.Health_Care_Backend.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthController {

    @Autowired
    private AuthService authService;



    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserInfo userInfo) {
        return authService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()) {


            AuthResponseDto authResponseDto = authService.findUserByUserName(authRequestDto.getUserName());

            return ResponseEntity.ok(authResponseDto);

        }else{
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }
}
