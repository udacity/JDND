package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
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
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User user = new User();
        user.setUsername("username_test");
        user.setPassword(encodePassword("password"));

        User saveUser = userRepository.save(user);

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/user/username_test"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.id", is((int)saveUser.getId())))
                .andExpect(jsonPath("$.username", is(saveUser.getUsername())));
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
                get(new URI("/api/user/username")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findById_http_ok() throws Exception {
        //given
        User user = new User();
        user.setUsername("test_by_id");
        user.setPassword(encodePassword("password"));

        User saveUser = userRepository.save(user);
        int id = (int) saveUser.getId();

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/user/id/" + id))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is(saveUser.getUsername())));
    }

    @Test
    public void findById_http_not_found() throws Exception {
        //expect
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/user/id/1001"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_http_forbidden() throws Exception {
        //expect
        ResultActions resultActions = mvc.perform(
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

    @Test
    public void createIUser() throws Exception {
        //given
        User user = getUser();
        user.setId(1l);

    }

    private User getUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(getCart());

        return user;
    }

    private Cart getCart() {
        Cart cart = new Cart();
        cart.setId(1l);
        cart.setItems(Lists.list(getItem()));
        cart.setTotal(BigDecimal.valueOf(20));

        return cart;
    }

    private Item getItem() {
        Item item = new Item();
        item.setName("itemName");
        item.setDescription("itemDescription");
        item.setPrice(BigDecimal.valueOf(11));

        return item;
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String getValidJwtToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjg3NTIwNzk1fQ.Pcef87avJHpAu6ZN84yiJYPxz2NNcSyAdcerfaKEua2rJpBNMZDVoU4gi_-F5W0rDwyy3ndcTn6964WCl6W3Gw";
    }
}