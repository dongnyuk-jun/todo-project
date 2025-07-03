package com.todoproject.backend.controller;

import com.todoproject.backend.dto.auth.LoginRequestDto;
import com.todoproject.backend.dto.auth.LoginResponseDto;
import com.todoproject.backend.dto.auth.SignupResponseDto;
import com.todoproject.backend.dto.auth.SignupRequestDto;
import com.todoproject.backend.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    // 생성자
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = authService.signup(signupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
