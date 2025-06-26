package com.todoproject.backend.service;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.domain.Todo;
import com.todoproject.backend.repository.UserRepository;
import com.todoproject.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    // 할 일 추가
    public Todo addTodo(String userId, String title) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTitle(title);

        return todoRepository.save(todo);
    }

    public List<Todo> getTodosByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return todoRepository.findByUser(user);
    }
}
