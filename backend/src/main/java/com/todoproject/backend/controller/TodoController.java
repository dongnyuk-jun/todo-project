package com.todoproject.backend.controller;

import com.todoproject.backend.domain.Todo;
import com.todoproject.backend.service.TodoService;
import com.todoproject.backend.config.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
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

    // 할 일 추가
    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody TodoRequest todoRequest, HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest);
        String userId = jwtTokenProvider.getUserId(token);

        Todo todo = todoService.addTodo(userId, todoRequest.getTitle());
        return ResponseEntity.ok(todo);
    }

    // 할 일 조회
    @GetMapping
    public ResponseEntity<List<Todo>> getUserTodos(HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest);
        String userId = jwtTokenProvider.getUserId(token);

        List<Todo> todos = todoService.getTodosByUserId(userId);
        return ResponseEntity.ok(todos);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static class TodoRequest {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
