package com.todoproject.backend.service.impl;

import com.todoproject.backend.config.jwt.JwtTokenProvider;
import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.auth.LoginRequestDto;
import com.todoproject.backend.dto.auth.LoginResponseDto;
import com.todoproject.backend.dto.auth.SignupRequestDto;
import com.todoproject.backend.dto.auth.SignupResponseDto;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.service.AuthService;
import io.jsonwebtoken.Jwt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원가입 로직
    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        // 1. userId 중복 검사
        Optional<User> existingUser = userRepository.findByUserId((signupRequestDto.getUserId()));
        if(existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다. ");
        }

        // 2. nickname 중복 검사
        if(userRepository.existsByNickname(signupRequestDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다. ");
        }

        // 3. 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // 4. User 엔티티 생성 및 저장
        User user = new User(
                signupRequestDto.getUserId(),
                encodePassword,
                signupRequestDto.getEmail(),
                signupRequestDto.getNickname(),
                "ROEL_USER"
        );

        // 5. 저장
        userRepository.save(user);

        // 6. 응답 반환
        return new SignupResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                "회원가입이 성공적으로 완료되었습니다. "
        );
    }

    // 로그인
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 1. userId로 사용자 조회
        Optional<User> userOptional = userRepository.findByUserId(loginRequestDto.getUserId());

        if(userOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다. ");
        }

        User user = userOptional.get();

        // 2. 비밀번호 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }

        // 3. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUserId(), user.getRole());

        // 4. 응답 반환
        return new LoginResponseDto(
                token,
                user.getUserId(),
                user.getNickname(),
                "로그인에 성공하였습니다. "
        );
    }
}
