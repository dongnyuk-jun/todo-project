package com.todoproject.backend.service;

import com.todoproject.backend.dto.todo.TodoRequestDto;
import com.todoproject.backend.dto.todo.TodoResponseDto;

public interface TodoService {
    TodoResponseDto createTodo(TodoRequestDto requestDto, String userId);
}
