package com.example.springbootthymeleaf.model;

public class User {

    public Integer id;
    public String name;
    public Integer age;

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
