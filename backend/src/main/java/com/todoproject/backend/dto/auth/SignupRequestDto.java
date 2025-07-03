package com.todoproject.backend.dto.auth;

public class SignupRequestDto {
    private String userId;
    private String password;
    private String email;
    private String nickname;

    public SignupRequestDto() {}

    public SignupRequestDto(String userId, String password, String email, String nickname) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() { return userId; }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
