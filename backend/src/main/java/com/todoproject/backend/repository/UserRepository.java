package com.todoproject.backend.repository;

import com.todoproject.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// 사용자 정보 repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserId(String userId);     // 로그인 시 사용
    //boolean existsByUserId(String userId);          // 중복 검사
}