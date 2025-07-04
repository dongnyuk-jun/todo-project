package com.todoproject.backend.service;

import com.todoproject.backend.dto.user.UserInfoResponseDto;

public interface UserService {
    UserInfoResponseDto getMyInfo(String userId);
}
