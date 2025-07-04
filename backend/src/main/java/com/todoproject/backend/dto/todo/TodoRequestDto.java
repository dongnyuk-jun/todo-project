package com.todoproject.backend.dto.todo;

import java.time.LocalDateTime;

public class TodoRequestDto {
    private String title;
    private String description;
    private LocalDateTime dueDate;

    public TodoRequestDto() {}

    public TodoRequestDto(String title, String description, LocalDateTime dueTime) {
        this.title = title;
        this.description = description;
        this.dueDate = dueTime;
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
}
