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
    public ResponseEntity<List<TodoResponseDto>> getTodos(HttpServletRequest request,
                                                          @RequestParam(required = false) Boolean isCompleted,
                                                          @RequestParam(defaultValue = "dueDate") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String direction,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        // 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 할 일 목록 조회
        List<TodoResponseDto> todos = todoService.getTodos(userId, isCompleted, sortBy, direction, page, size);

        return ResponseEntity.ok(todos);
    }

    // 할 일 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 사용자 ID  추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 할 일 수정
        TodoResponseDto updated = todoService.updateTodo(id, requestDto, userId);

        return ResponseEntity.ok(updated);
    }

    // 할 일 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, HttpServletRequest request) {
        // 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 할 일 삭제
        todoService.deleteTodo(id, userId);

        return ResponseEntity.noContent().build();
    }

    // 할 일 완료 여부 변경 API
    @PatchMapping("/{id}/done")
    public ResponseEntity<TodoResponseDto> toggleDone(@PathVariable Long id, HttpServletRequest request) {
        // 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(token);

        // 3. 할 일 완료 여부 변경
        TodoResponseDto completed = todoService.toggleDone(id, userId);

        return ResponseEntity.ok(completed);
    }
}
