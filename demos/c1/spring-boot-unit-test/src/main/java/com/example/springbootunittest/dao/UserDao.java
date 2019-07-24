package com.example.springbootunittest.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    public String getUser() {
        return "Jerry";
    }
}
