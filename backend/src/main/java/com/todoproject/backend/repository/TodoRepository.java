package com.todoproject.backend.repository;

import com.todoproject.backend.domain.User;
import com.todoproject.backend.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // To Do list 반환
    List<Todo> findByUser(User user);

    // 사용자 + 완료 여부 List 반환
    //List<Todo> findByUserAndCompleted(User user, Boolean completed);
    @Query("SELECT t FROM Todo t WHERE t.user = :user ORDER BY t.dueDate DESC")
    Page<Todo> findByUser(@Param("user") User user, Pageable pageable);
    Page<Todo> findByUserAndCompleted(User user, Boolean completed, Pageable pageable);
}
