package com.todoproject.backend.controller;

import com.todoproject.backend.config.auth.CustomUserDetails;
import com.todoproject.backend.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 로그인 요청 처리, JWT 발급
@RestController
@RequestMapping("/api")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData, HttpServletResponse response) throws IOException {
        String userId = loginData.get("userId");
        String password = loginData.get("password");

        try {
            // 1. 인증 시도
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));

            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            String role = userDetails.getUser().getRole();

            // 2. JWT 생성
            String token = jwtTokenProvider.createToken(userId, role);

            // 3. 응답 반환
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("userId", userId);
            return result;
        } catch (AuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            return null;
        }
    }
}
