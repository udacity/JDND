package com.example.demo.model.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ItemTest {

    @Test
    void hashCode_ItemIdIsNull() {
        Item item = createItem("name", "description", BigDecimal.ZERO);

        int hashCode = item.hashCode();

        Assertions.assertEquals(31, hashCode);
    }

    @Test
    void hashCode_ItemIdIsNotNull() {
        Item item = createItem("name", "description", BigDecimal.ZERO);
        item.setId(10l);

        int hashCode = item.hashCode();

        Assertions.assertEquals(41, hashCode);
    }

    @Test
    void Equals_ItemIsEqual() {
        Item item = createItem("name", "desciption", BigDecimal.ZERO);
        item.setId(1l);

        boolean equals = item.equals(item);

        Assertions.assertTrue(equals);
    }

    @Test
    void Equals_ItemInNotEqual() {
        Item item = createItem("name", "description", BigDecimal.ZERO);
        item.setId(1l);
        Item comparingItem = createItem("name1", "description1", BigDecimal.TEN);
        item.setId(1l);

        boolean equals = item.equals(comparingItem);

        Assertions.assertFalse(equals);
    }

    @Test
    void Equals_ObjectIsNull() {
        Item item = createItem("name1", "description", BigDecimal.TEN);
        item.setId(1l);

        boolean equals = item.equals(null);

        Assertions.assertFalse(equals);
    }

    @Test
    void Equals_ClassIsDifferent() {
        Item item = createItem("name1", "description", BigDecimal.TEN);
        item.setId(1l);

        boolean equals = item.equals("comaparing");

        Assertions.assertFalse(equals);
    }

    @Test
    void Equals_IdIsNull() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        Item comparingItem = createItem("name1", "description1", BigDecimal.TEN);
        item.setId(2l);
        boolean equals = item.equals(comparingItem);

        Assertions.assertFalse(equals);
    }

    @Test
    void Equals_IdIsNotEqual() {
        Item item = createItem("name", "description", BigDecimal.TEN);
        item.setId(1l);
        Item comparingItem = createItem("name", "description", BigDecimal.TEN);
        item.setId(2l);
        boolean equals = item.equals(comparingItem);

        Assertions.assertFalse(equals);
    }
    private Item createItem(String name, String description, BigDecimal price) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);

        return item;
    }
}