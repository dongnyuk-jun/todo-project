package com.todoproject.backend.controller;


import com.todoproject.backend.config.jwt.JwtTokenProvider;
import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;
import com.todoproject.backend.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;
    private final JwtTokenProvider jwtTokenProvider;

    public TodoController(TodoService todoService, JwtTokenProvider jwtTokenProvider) {
        this.todoService = todoService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 할 일 생성 API
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        // 1. 요청 헤더에서 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 토큰에서 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 서비스에 등록 요청
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userId);

        return ResponseEntity.ok(responseDto);
    }

    // 할 일 조회 API
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getTodos(HttpServletRequest request) {
        // 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 할 일 목록 조회
        List<TodoResponseDto> todos = todoService.getTodos(userId);

        return ResponseEntity.ok(todos);
    }
}
