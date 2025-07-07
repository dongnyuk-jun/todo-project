package com.todoproject.backend.service.impl;

import com.todoproject.backend.domain.Todo;
import com.todoproject.backend.domain.User;
import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;
import com.todoproject.backend.repository.TodoRepository;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.service.TodoService;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<TodoResponseDto> getTodos(String userId, Boolean completed, String sortBy, String direction, int page, int size) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Todo> todoPage;

        if(completed != null) {
            todoPage = todoRepository.findByUserAndCompleted(user, completed, pageable);
        }
        else {
            todoPage = todoRepository.findByUser(user, pageable);
        }

        return todoPage.stream().map(
                todo -> new TodoResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getDueDate(),
                        todo.isCompleted(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                )
        ).collect(Collectors.toList());

        /*
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
         */
    }

    // 수정
    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();

        Optional<Todo> todoOpt = todoRepository.findById(id);
        if(todoOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 할 일을 찾을 수 없습니다. ");
        }

        Todo todo = todoOpt.get();

        if(!todo.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 할 일만 수정할 수 있습니다. ");
        }

        // 수정
        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setDueDate(requestDto.getDueDate());

        // 저장
        todoRepository.save(todo);

        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getDueDate(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }

    // 삭제
    @Override
    public void deleteTodo(Long id, String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();

        Optional<Todo> todoOpt = todoRepository.findById(id);
        if(todoOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 할 일을 찾을 수 없습니다. ");
        }

        Todo todo = todoOpt.get();

        if(!todo.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 할 일만 삭제할 수 있습니다. ");
        }

        todoRepository.delete(todo);
    }

    // 완료
    @Override
    public TodoResponseDto toggleDone(Long id, String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ");
        }

        User user = userOpt.get();

        Optional<Todo> todoOpt = todoRepository.findById(id);
        if(todoOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 할 일을 찾을 수 없습니다. ");
        }

        Todo todo = todoOpt.get();

        if(!todo.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 할 일만 완료 상태를 변경할 수 있습니다. ");
        }

        todo.setCompleted(!todo.isCompleted());

        todoRepository.save(todo);

        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getDueDate(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
