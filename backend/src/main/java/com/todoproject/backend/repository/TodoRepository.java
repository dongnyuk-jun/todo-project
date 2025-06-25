package com.todoproject.backend.repository;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // To Do list 반환
    List<Todo> findByUser(User user);
}
