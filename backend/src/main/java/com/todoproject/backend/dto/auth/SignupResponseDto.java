package com.todoproject.backend.dto.auth;

public class SignupResponseDto {
    private String userId;
    private String email;
    private String nickname;
    private String message;

    public SignupResponseDto() {}

    public SignupResponseDto(String userId, String email, String nickname, String message) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
