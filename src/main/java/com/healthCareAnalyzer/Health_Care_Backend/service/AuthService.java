package com.healthCareAnalyzer.Health_Care_Backend.service;

import com.healthCareAnalyzer.Health_Care_Backend.dto.AuthResponseDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserInfo;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added to system";
    }

    public AuthResponseDto findUserByUserName(String userName) {
        Optional<UserInfo> userInfo = userInfoRepository.findByUserName(userName);
        if(userInfo.isPresent()) {
            UserInfo user = userInfo.get();
            String token = jwtService.generateJwtToken(userName);
            return new AuthResponseDto(user.getUserName(),user.getFirstName(),user.getLastName(), user.getRoles(),token);
        }else{
            throw new UsernameNotFoundException("User not found with "+userName);
        }

    }
}
