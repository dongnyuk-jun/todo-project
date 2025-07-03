package com.todoproject.backend.dto.auth;

public class LoginRequestDto {
    private String userId;
    private String password;

    public LoginRequestDto() {}
    public LoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
