package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void add_to_cart_happy_path() {
        User user = TestUtils.createUser("testUsername", "testPassword");
        Cart cart = TestUtils.createCart();
        user.setCart(cart);
        when(userRepo.findByUsername("testUsername")).thenReturn(user);

        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item1));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Cart returnedCart = response.getBody();

        assertNotNull(returnedCart);
        assertEquals(returnedCart, cart);
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(itemRepo, times(1)).findById(1L);
    }

    @Test
    public void add_to_cart_with_invalid_user() {
        when(userRepo.findByUsername("testUsername")).thenReturn(null);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void add_to_cart_with_invalid_item() {
        when(itemRepo.findById(1L)).thenReturn(Optional.empty());

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void remove_from_cart_happy_path() {
        User user = TestUtils.createUser("testUsername", "testPassword");
        Cart cart = TestUtils.createCart();
        user.setCart(cart);
        when(userRepo.findByUsername("testUsername")).thenReturn(user);

        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item1));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Cart returnedCart = response.getBody();

        assertNotNull(returnedCart);
        assertEquals(returnedCart, cart);
        Mockito.verify(userRepo, times(1)).findByUsername("testUsername");
        Mockito.verify(itemRepo, times(1)).findById(1L);
    }
    
    @Test
    public void remove_from_cart_with_invalid_user() {
        when(userRepo.findByUsername("testUsername")).thenReturn(null);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void remove_from_cart_with_invalid_item() {
        when(itemRepo.findById(1L)).thenReturn(Optional.empty());

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(5);
        request.setUsername("testUsername");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
}
