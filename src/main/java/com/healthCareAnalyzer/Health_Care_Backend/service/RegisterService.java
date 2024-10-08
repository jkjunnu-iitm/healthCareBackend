package com.healthCareAnalyzer.Health_Care_Backend.service;

import com.healthCareAnalyzer.Health_Care_Backend.dto.RegisterRequestDto;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserInfoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterService {

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;


    public RegisterService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public String addUser(@Valid RegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        userEntity.setUserName(registerRequestDto.getUserName());
        userEntity.setRoles(registerRequestDto.getRoles());
        userEntity.setFirstName(registerRequestDto.getFirstName());
        userEntity.setLastName(registerRequestDto.getLastName());
        userInfoRepository.save(userEntity);
        return "User added to system";
    }


}
