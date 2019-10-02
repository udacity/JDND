package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepo = mock(UserRepository.class);

    private OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void submit_with_valid_username() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");

        Cart cart = new Cart();

        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);
        cart.addItem(item1);
        Item item2 = TestUtils.createItem(2L, "Item 2", "Description 2", BigDecimal.valueOf(20));
        cart.addItem(item2);

        cart.setUser(user);

        user.setCart(cart);
        when(userRepo.findByUsername("testUsername")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("testUsername");
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        UserOrder userOrder = response.getBody();

        assertNotNull(userOrder);
        assertEquals(Arrays.asList(item1, item2), userOrder.getItems());
        assertEquals(user, userOrder.getUser());
        assertEquals(BigDecimal.valueOf(30), userOrder.getTotal());
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(orderRepo, times(1)).save(userOrder);
    }

    @Test
    public void submit_with_invalid_username() {
        when(userRepo.findByUsername("testUsername")).thenReturn(null);

        ResponseEntity<UserOrder> response = orderController.submit("testUsername");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(orderRepo, never()).save(Mockito.any());
    }

    @Test
    public void get_history_with_valid_username() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");

        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);

        UserOrder userOrder1 = new UserOrder();
        userOrder1.setUser(user);
        userOrder1.setItems(Arrays.asList(item1));
        userOrder1.setTotal(BigDecimal.TEN);

        UserOrder userOrder2 = new UserOrder();
        userOrder2.setUser(user);
        userOrder2.setItems(Arrays.asList(item1));
        userOrder2.setTotal(BigDecimal.TEN);

        when(userRepo.findByUsername("testUsername")).thenReturn(user);
        when(orderRepo.findByUser(user)).thenReturn(Arrays.asList(userOrder1, userOrder2));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUsername");
        assertNotNull(response);
        List<UserOrder> responseBody = response.getBody();
        assertEquals(Arrays.asList(userOrder1, userOrder2), responseBody);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(orderRepo, times(1)).findByUser(user);
    }

    @Test
    public void get_history_with_invalid_username() {
        when(userRepo.findByUsername("testUsername")).thenReturn(null);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUsername");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(orderRepo, never()).save(Mockito.any());
    }
}
