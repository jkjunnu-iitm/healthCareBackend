package com.healthCareAnalyzer.Health_Care_Backend.controller.auth;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login.LoginRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.login.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {


    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
                if (authentication.isAuthenticated()) {

                    LoginResponseDto loginResponseDto;
                    loginResponseDto = loginService.findUserByUsername(loginRequestDto.getUsername());

                    return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);

                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password is incorrect");
                }
            } else {
                log.info(bindingResult.getAllErrors().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields are not valid");

            }

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password is incorrect");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
