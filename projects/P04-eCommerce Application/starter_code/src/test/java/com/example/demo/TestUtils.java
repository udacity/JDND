package com.example.demo;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject) {

        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if (!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);

            if (wasPrivate) {
                f.setAccessible(false);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public static Item createItem(Long id, String name, String description, BigDecimal price) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        return item;
    }

    public static Cart createCart() {
        Cart cart = new Cart();
        Item item1 = createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);
        cart.addItem(item1);
        Item item2 = createItem(2L, "Item 2", "Description 2", BigDecimal.valueOf(20));
        cart.addItem(item2);
        return cart;
    }
}
