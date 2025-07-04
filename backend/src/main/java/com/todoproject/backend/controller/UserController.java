package com.todoproject.backend.controller;

import com.todoproject.backend.dto.user.UserInfoResponseDto;
import com.todoproject.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 인증된 사용자 정보 반환
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(Authentication authentication) {
        String userId = authentication.getName();
        UserInfoResponseDto response = userService.getMyInfo(userId);
        return ResponseEntity.ok(response);
    }
}
