package com.example.authsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    private String username;

    @NotNull(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    private String birthDate;
    private String cep;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}
    public String getCep() {return cep;}
    public void setCep(String cep) {this.cep = cep;}
}
