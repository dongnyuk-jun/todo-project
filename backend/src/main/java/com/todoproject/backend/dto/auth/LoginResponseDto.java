package com.todoproject.backend.dto.auth;

public class LoginResponseDto {
    private String token;
    private String userId;
    private String nickname;
    private String message;

    public LoginResponseDto() {}

    public LoginResponseDto(String token, String userId, String nickname, String message) {
        this.token = token;
        this.userId = userId;
        this.nickname = nickname;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
