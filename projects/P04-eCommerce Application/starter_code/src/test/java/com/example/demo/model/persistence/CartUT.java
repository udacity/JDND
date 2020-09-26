package com.example.demo.model.persistence;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class CartUT {

    @Test
    void addItem_whenItemAndTotalIsNull() {
        Cart cart = createCart(null, null);
        Item item = createItem("name", "description", BigDecimal.TEN);

        cart.addItem(item);

        Assertions.assertEquals(item, cart.getItems().get(0));
        Assertions.assertEquals(item.getPrice(), cart.getTotal());
    }

    @Test
    void addItem_whenItemAndTotalIsNotNull() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        Cart cart = createCart(Lists.list(item, item), BigDecimal.ONE);

        cart.addItem(item);

        Assertions.assertEquals(3, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(11), cart.getTotal());
    }

    @Test
    void removeItem_whenRemovingItemIsNotInCartAndItemPriceIsZero() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        item.setId(1l);
        Cart cart = createCart(Lists.list(item), BigDecimal.valueOf(100));
        Item removeItem = createItem("removeItem", "description", BigDecimal.ZERO);
        removeItem.setId(2l);

        cart.removeItem(removeItem);

        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(100), cart.getTotal());
    }

    @Test
    void removeItem_whenRemovingItemIsNotInCartAndItemPriceIsNotZero() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        item.setId(1l);
        Cart cart = createCart(Lists.list(item), BigDecimal.valueOf(100));
        Item removeItem = createItem("removeItem", "description", BigDecimal.ONE);
        removeItem.setId(2l);

        cart.removeItem(removeItem);

        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(99), cart.getTotal());
    }

    @Test
    void removeItem_whenRemovingitemIsInCart() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        item.setId(1l);
        Cart cart = createCart(Lists.list(item), BigDecimal.valueOf(100));


        cart.removeItem(item);
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(90), cart.getTotal());
    }

    @Test
    void removeItem_whenItemAndTotalInCartAreNull() {
        Item item = createItem("name", "description", BigDecimal.ZERO);
        item.setId(1l);
        Cart cart = createCart(null, null);


        cart.removeItem(item);
        Assertions.assertEquals(new ArrayList<>(), cart.getItems());
        Assertions.assertEquals(BigDecimal.ZERO, cart.getTotal());
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
        user.setPassword("Password");
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