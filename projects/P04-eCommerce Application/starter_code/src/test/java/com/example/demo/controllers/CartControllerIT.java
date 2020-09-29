package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
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

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Cart cart = cartRepository.save(new Cart());
        User user = userRepository.findByUsername("test_username");
        user.setCart(cart);
        userRepository.save(user);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1l);
        request.setQuantity(1);

        //when
        ResultActions resultActions = mvc.perform(
                post(new URI("/api/cart/addToCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //expect
        resultActions
                .andExpect(jsonPath("$.user.id", is((int) user.getId())))
                .andExpect(jsonPath("$.user.username", is(user.getUsername())))
                .andExpect(jsonPath("$.items.[0].id", is(1)))
                .andExpect(jsonPath("$.items.[0].name", is("Round Widget")));
    }

    @Test
    public void addTocart_http_not_found_user() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("invalid_test_username");
        request.setItemId(1l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/addToCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addTocart_http_not_found_item() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1000090l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/addToCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addTocart_http_forbidden() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/addToCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void removeFromcart_http_ok() throws Exception {
        //given
        Optional<Item> itemRepositoryById = itemRepository.findById(1l);
        Cart cart = new Cart();
        cart.setItems(Lists.list(itemRepositoryById.get()));

        Cart saveCart = cartRepository.save(cart);

        User user = userRepository.findByUsername("test_username");
        user.setCart(saveCart);
        userRepository.save(user);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1l);
        request.setQuantity(1);

        //when
        ResultActions resultActions = mvc.perform(
                post(new URI("/api/cart/removeFromCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //expect
        resultActions
                .andExpect(jsonPath("$.user.id", is((int) user.getId())))
                .andExpect(jsonPath("$.user.username", is(user.getUsername())))
                .andExpect(jsonPath("$.items", hasSize(0)));
    }

    @Test
    public void removeFromcart_http_not_found_user() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("invalid_test_username");
        request.setItemId(1l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/removeFromCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeFromcart_http_not_found_item() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1000090l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/removeFromCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeFromcart_http_forbidden() throws Exception {
        //given
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test_username");
        request.setItemId(1l);
        request.setQuantity(10);

        //expect
        mvc.perform(
                post(new URI("/api/cart/removeFromCart"))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
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
