package com.todoproject.backend.config.auth;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 사용자 정보를 가져와 인증 처리
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // userId를 기준으로 사용자 정보 조회
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        return new CustomUserDetails(user);
    }
}
