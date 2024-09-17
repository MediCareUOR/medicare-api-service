package com.rucreativedeveloper.medicare_api_service.dto.request;

public class RequestUserLoginDto {
    private String username;
    private String password;

    public RequestUserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RequestUserLoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
