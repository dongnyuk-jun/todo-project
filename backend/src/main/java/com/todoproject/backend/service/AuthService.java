package com.todoproject.backend.service;

import com.todoproject.backend.dto.auth.LoginRequestDto;
import com.todoproject.backend.dto.auth.LoginResponseDto;
import com.todoproject.backend.dto.auth.SignupRequestDto;
import com.todoproject.backend.dto.auth.SignupResponseDto;

public interface AuthService {
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
