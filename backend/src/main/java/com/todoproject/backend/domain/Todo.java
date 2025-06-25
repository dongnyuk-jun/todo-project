package com.todoproject.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 할 일 제목
    @Column(nullable = false)
    private String title;

    // 기타 설명 (선택)
    @Column
    private String description;

    // 기한 (선택)
    @Column
    private LocalDateTime dueDate;

    // 완료 여부
    @Column(nullable = false)
    private boolean completed = false;

    // 사용자 ID
    @Column(nullable = false)
    private String userId;

    // 생성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {}

    public Todo(String title, String description, LocalDateTime dueDate, String userId) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.userId = userId;
        createdAt = LocalDateTime.now();
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
