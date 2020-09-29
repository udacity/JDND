package com.example.demo.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class LoginControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void login_http_ok() throws Exception {
        //given
        String loginRequest = "{\"username\":\"test_username\",\"password\":\"password\"}";

        //expect
        mvc.perform(
                post(new URI("/login"))
                        .content(loginRequest)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void login_http_unauthorized() throws Exception {
        //given
        String invalidLoginRequest = "{\"username\":\"test_username\",\"password\":\"invalid_password\"}";

        //expect
        mvc.perform(
                post(new URI("/login"))
                        .content(invalidLoginRequest)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
