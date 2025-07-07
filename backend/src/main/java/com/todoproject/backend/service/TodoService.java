package com.todoproject.backend.service;

import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto createTodo(TodoRequestDto requestDto, String userId);
    List<TodoResponseDto> getTodos(String userId, Boolean isCompleted, String sortBy, String direction, int page, int size);
    TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, String userId);
    void deleteTodo(Long id, String userId);
    TodoResponseDto toggleDone(Long id, String userId);
}
