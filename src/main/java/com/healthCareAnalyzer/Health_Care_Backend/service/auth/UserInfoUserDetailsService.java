package com.healthCareAnalyzer.Health_Care_Backend.service.auth;

import com.healthCareAnalyzer.Health_Care_Backend.config.UserInfoUserDetails;
import com.healthCareAnalyzer.Health_Care_Backend.entity.UserEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public UserInfoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userInfo = userRepository.findByUsername(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));


    }
}
