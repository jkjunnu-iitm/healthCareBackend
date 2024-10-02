package com.healthCareAnalyzer.Health_Care_Backend.service;

import com.healthCareAnalyzer.Health_Care_Backend.dto.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;

    public LoginService(UserInfoRepository userInfoRepository, JwtService jwtService) {
        this.userInfoRepository = userInfoRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDto findUserByUserName(String userName) {
        Optional<UserEntity> userInfo = userInfoRepository.findByUserName(userName);
        if (userInfo.isPresent()) {
            UserEntity user = userInfo.get();
            String token = jwtService.generateJwtToken(userName);
            return new LoginResponseDto(token, user.getUserName(), user.getFirstName(), user.getLastName(), user.getRoles());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found with " + userName);
        }

    }
}
