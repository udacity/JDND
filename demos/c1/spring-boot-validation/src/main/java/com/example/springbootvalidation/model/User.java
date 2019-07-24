package com.example.springbootvalidation.model;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "username cannot be empty")
    private String name;
    @NotBlank(message = "password cannot be empty")
    @Length(min=6, max=10, message="length of password should between 6 to 10 characters")
    private String password;
    @Min(value=0)
    @Max(value=100)
    private Double grade;
    @Email
    private String email;

    public User(String name, String password, Double grade, String email) {
        this.name = name;
        this.password = password;
        this.grade = grade;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
