package com.todoproject.backend.domain;

import jakarta.persistence.*;       // GPT한테 javax랑 jakarta랑 물어보기
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 사용자 정보 Entity
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 기본키

    @Column(nullable = false, unique = true)
    private String userId;      // 로그인 용 id

    @Column(nullable = false)
    private String password;        // 비밀번호

    @Column(nullable = false)
    private String email;           // 이메일

    @Column(nullable = false, unique = true)
    private String nickname;        // 닉네임

    @Column(nullable = false)
    private String role;        // 권한, ROLE_USER || ROLE_ADMIN

    @Column(nullable = false)
    private LocalDateTime createdAt;        // 생성일

    // 기본 생성자
    public User() {}

    // 전체 필드 생성자
    public User(String userId, String password, String email, String nickname, String role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }


    // Getter
    public Long getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getNickname() {
        return nickname;
    };
    public String getRole() {
        return role;
    }

    // Setter
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setRole(String role) {
        this.role = role;
    }
}