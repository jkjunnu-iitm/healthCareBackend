package com.healthCareAnalyzer.Health_Care_Backend.service.auth.login;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserRepository;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDto findUserByUsername(String username) {
        Optional<UserEntity> userInfo = userRepository.findUser(username);
        if (userInfo.isPresent()) {
            UserEntity user = userInfo.get();
            String token = jwtService.generateJwtToken(username);
            return new LoginResponseDto(token, user);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found with " + username);
        }

    }

    public ResponseEntity<?> authenticateUser(@NotBlank @Email(message = "Invalid email id") String username, @NotBlank @Size(min = 4, max = 20, message = "Min password length 4") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {

            LoginResponseDto loginResponseDto;
            loginResponseDto = findUserByUsername(username);

            return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);

        } else {
            throw new UsernameNotFoundException("Username or password is incorrect");
        }
    }
}
