package com.healthCareAnalyzer.Health_Care_Backend.service.auth.login;

import com.healthCareAnalyzer.Health_Care_Backend.dto.auth.login.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserRepository;
import com.healthCareAnalyzer.Health_Care_Backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public LoginService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDto findUserByUsername(String username) {
        Optional<UserEntity> userInfo = userRepository.findByUsername(username);
        if (userInfo.isPresent()) {
            UserEntity user = userInfo.get();
            String token = jwtService.generateJwtToken(username);
            return new LoginResponseDto(token, user.getUsername(), user.getFirstName(), user.getLastName(), user.getRole());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found with " + username);
        }

    }
}
