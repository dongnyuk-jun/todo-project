package com.todoproject.backend.config.jwt;

import com.todoproject.backend.config.auth.CustomUserDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 AUthorization 값 추출
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                // 2. 토큰 유효성 검사
                if(jwtTokenProvider.validateToken(token)) {
                    // 3. 토큰에서 사용자 ID 추출
                    String userId = jwtTokenProvider.getUserId(token);

                    // 4. 사용자 정보 조회
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

                    // 5. 인증 객체 생성 후 SecurityContext에 등록
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("\uD83D\uDD25userId from token: " + userId);
                    System.out.println("✅authentication 등록: " + authentication.getName());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException | IllegalArgumentException e) {
                logger.warn("JWT 처리 중 오류 발생: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
