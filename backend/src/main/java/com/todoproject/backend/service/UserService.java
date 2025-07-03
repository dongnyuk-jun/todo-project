package com.todoproject.backend.service;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.auth.SignupRequestDto;
import com.todoproject.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public void registerUser(SignupRequestDto dto) {
        if(userRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        User user = new User(dto.getUserId(), passwordEncoder.encode(dto.getPassword()), dto.getEmail(), dto.getNickname(), "ROLE_USER");

        userRepository.save(user);
    }
}
