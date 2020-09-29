package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create_user_http_ok() throws Exception {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setConfirmPassword("password");

        //when
        ResultActions resultActions = mvc.perform(
                post(new URI("/api/user/create"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.id", is(any(int.class))))
                .andExpect(jsonPath("$.username", is(request.getUsername())));
    }

    @Test
    public void create_user_http_bad_request() throws Exception {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("pas");
        request.setConfirmPassword("pas");

        //expect
        mvc.perform(
                post(new URI("/api/user/create"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findByUserName_http_ok() throws Exception {
        //given
        User user = userRepository.findByUsername("test_username");

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/user/test_username"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.id", is((int) user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void findByUserName_http_not_found() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/user/invalid_username"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByUserName_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/user/test_username")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findById_http_ok() throws Exception {
        //given
        User user = userRepository.findByUsername("test_username");
        int id = (int) user.getId();

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/user/id/" + id))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void findById_http_not_found() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/user/id/1001"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/user/id/1001")))
                .andExpect(status().isForbidden());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValidJwtToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjg3NTIwNzk1fQ.Pcef87avJHpAu6ZN84yiJYPxz2NNcSyAdcerfaKEua2rJpBNMZDVoU4gi_-F5W0rDwyy3ndcTn6964WCl6W3Gw";
    }
}