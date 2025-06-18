package com.todoproject.backend.domain;

import jakarta.persistence.*;       // GPT한테 javax랑 jakarta랑 물어보기
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


}