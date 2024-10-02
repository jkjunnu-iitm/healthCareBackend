package com.healthCareAnalyzer.Health_Care_Backend.service;

import com.healthCareAnalyzer.Health_Care_Backend.dto.LoginResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserInfo;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        Optional<UserInfo> userInfo = userInfoRepository.findByUserName(userName);
        if (userInfo.isPresent()) {
            UserInfo user = userInfo.get();
            String token = jwtService.generateJwtToken(userName);
            return new LoginResponseDto(token, user.getUserName(), user.getFirstName(), user.getLastName(), user.getRoles());
        } else {
            throw new UsernameNotFoundException("User not found with " + userName);
        }

    }
}
