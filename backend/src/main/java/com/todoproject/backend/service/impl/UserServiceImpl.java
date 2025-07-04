package com.todoproject.backend.service.impl;


import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.user.UserInfoResponseDto;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 현재 로그인한 사용자 정보 조회
    public UserInfoResponseDto getMyInfo(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();
        return new UserInfoResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole()
        );
    }
}
