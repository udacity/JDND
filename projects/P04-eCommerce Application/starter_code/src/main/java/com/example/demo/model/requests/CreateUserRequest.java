package com.example.demo.model.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

    @JsonProperty
    @NotBlank(message = "usrname is mandatory")
    private String username;

    @JsonProperty
    @NotBlank(message = "password is mandatory")
    private String password;

    @JsonProperty
    @NotBlank(message = "confirmPassword is mandatory")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
