package com.todoproject.backend.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.security.Key;

@Component
public class JwtTokenProvider {

    //  시크릿 키와 유효 시간 설정
    //private final String secretKey = Base64.getEncoder().encodeToString("dongnyuk-1234".getBytes());
    private final SecretKey secretKey;
    private final long validityInMilliseconds = 100 * 60 * 60;      // 1시간

    public JwtTokenProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    private Key getSigningKey() {
        //return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return secretKey;
    }

    // 토큰 생성
    public String createToken(String userId, String role) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody().getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
