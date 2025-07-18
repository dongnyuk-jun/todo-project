package com.todoproject.backend.config.auth;

import com.todoproject.backend.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// User를 Spring Security에서 사용하는 UserDetails로 변환
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 권한 반환 - 단일 권한 사용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return Collections.emptyList();     // 권한이 필요없으면 빈 리스트
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    // 패스워드 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자 Id 반환
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    // 계정 유효 검사
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


    // 사용자 정보 Getter
    public User getUser() {
        return user;
    }
}
