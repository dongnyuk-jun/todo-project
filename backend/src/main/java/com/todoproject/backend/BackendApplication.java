package com.todoproject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.repository.UserRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// DB 연결용 테스트 코드
	@Bean
	public CommandLineRunner dbTest(UserRepository userRepository) {
		return args -> {
			// 테스트 유저 생성
			User user = new User(
					"testuser2",
					"1234",
					"test@example.com",
					"tester2",
					"ROLE_USER"
			);
			userRepository.save(user);

			// 조회 테스트
			User found = userRepository.findByUserId("testuser").orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. "));
			System.out.println("조회된 사용자 닉네임: " + found.getNickname());
		};
	}
}
