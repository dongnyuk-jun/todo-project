package com.todoproject.backend.service.impl;

import com.todoproject.backend.domain.Todo;
import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;
import com.todoproject.backend.repository.TodoRepository;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.service.TodoService;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    // 할 일 생성
    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. "));

        Todo todo = new Todo(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getDueDate(),
                user
        );

        Todo saved = todoRepository.save(todo);

        return new TodoResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDueDate(),
                saved.isCompleted(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
