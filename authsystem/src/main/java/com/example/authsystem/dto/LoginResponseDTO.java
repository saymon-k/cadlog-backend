package com.example.authsystem.dto;

public class LoginResponseDTO {
    private final String username;
    private final String email;

    public LoginResponseDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public String getUsername() {return username;}
    public String getEmail() {return email;}
}
