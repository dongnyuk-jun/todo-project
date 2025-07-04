package com.todoproject.backend.service.impl;

import com.todoproject.backend.domain.Todo;
import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;
import com.todoproject.backend.repository.TodoRepository;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // 조회
    @Override
    public List<TodoResponseDto> getTodos(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();

        // 해당 사용자의 할 일 목록 조회
        List<Todo> todoList = todoRepository.findByUser(user);

        // 응답 DTO로 변환
        List<TodoResponseDto> responseList = new ArrayList<>();
        for(Todo todo: todoList) {
            responseList.add(new TodoResponseDto(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getDescription(),
                    todo.getDueDate(),
                    todo.isCompleted(),
                    todo.getCreatedAt(),
                    todo.getUpdatedAt()
            ));
        }

        return responseList;
    }
}
