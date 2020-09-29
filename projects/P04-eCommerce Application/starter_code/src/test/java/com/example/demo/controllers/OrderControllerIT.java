package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class OrderControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void submit_http_ok() throws Exception {
        //TODO
    }

    @Test
    public void submit_http_not_found() throws Exception {
        //expect
        mvc.perform(
                post(new URI("/api/order/submit/submit_invalid_username"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void submit_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                post(new URI("/api/order/submit/username")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getOrdersForUser_http_ok() throws Exception {
        //TODO
    }

    @Test
    public void getOrdersForUser_http_not_found() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/order/history/username_notfound"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOrdersForUser_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/order/history/username")))
                .andExpect(status().isForbidden());
    }

    private String getValidJwtToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjg3NTIwNzk1fQ.Pcef87avJHpAu6ZN84yiJYPxz2NNcSyAdcerfaKEua2rJpBNMZDVoU4gi_-F5W0rDwyy3ndcTn6964WCl6W3Gw";
    }

    private Cart getCart() {
        Cart cart = new Cart();
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
}
