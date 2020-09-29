package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void addTocart_http_ok() throws Exception {
        //given
        User user = new User();
        user.setUsername("user_add_to_cartol");
        user.setPassword(encodePassword("password"));

        userRepository.save(user);

    }

    @Test
    public void addTocart_http_not_found_user() throws Exception {
    }

    @Test
    public void addTocart_http_not_found_item() throws Exception {
    }

    @Test
    void addTocart_http_forbidden() throws Exception {
    }

    private String getValidJwtToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjg3NTIwNzk1fQ.Pcef87avJHpAu6ZN84yiJYPxz2NNcSyAdcerfaKEua2rJpBNMZDVoU4gi_-F5W0rDwyy3ndcTn6964WCl6W3Gw";
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
