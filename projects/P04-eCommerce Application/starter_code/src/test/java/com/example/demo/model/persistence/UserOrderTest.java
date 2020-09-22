package com.example.demo.model.persistence;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class UserOrderTest {

    @Test
    void createFromCart() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        item.setId(1l);

        Cart cart = createCart(Lists.list(item), BigDecimal.valueOf(100));
        cart.setId(1l);

        UserOrder fromCart = UserOrder.createFromCart(cart);

        Assertions.assertEquals(1, fromCart.getItems().size());
        Assertions.assertEquals(item, fromCart.getItems().get(0));

        Assertions.assertEquals(1l, fromCart.getUser().getId());
        Assertions.assertEquals("username", fromCart.getUser().getUsername());
        Assertions.assertEquals("password", fromCart.getUser().getPassword());

        Assertions.assertEquals(cart.getTotal(), fromCart.getTotal());

    }

    private Cart createCart(List<Item> items, BigDecimal total) {
        Cart cart = new Cart();
        cart.setId(1l);
        cart.setItems(items);
        cart.setTotal(total);
        cart.setUser(createUser());

        return cart;
    }

    private User createUser() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setId(1l);

        return user;
    }
    private Item createItem(String name, String description, BigDecimal price) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);

        return item;
    }

}