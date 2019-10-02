package com.example.demo.controllers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void get_items() {
        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);
        Item item2 = TestUtils.createItem(2L, "Item 2", "Description 2", BigDecimal.valueOf(20));

        when(itemRepo.findAll()).thenReturn(Arrays.asList(item1, item2));

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<Item> items = response.getBody();

        assertNotNull(items);
        assertArrayEquals(Arrays.asList(item1, item2).toArray(), items.toArray());
        Mockito.verify(itemRepo, times(1)).findAll();
    }

    @Test
    public void get_item_by_id_happy_path() {
        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Item item = response.getBody();
        assertNotNull(item);
        assertEquals(Long.valueOf(1), item.getId());
        assertEquals("Item 1", item.getName());
        assertEquals("Description 1", item.getDescription());
        assertEquals(BigDecimal.TEN, item.getPrice());
        Mockito.verify(itemRepo, times(1)).findById(1L);
    }

    @Test
    public void get_item_by_id_with_invalid_id() {
        when(itemRepo.findById(1L)).thenReturn(Optional.empty());

        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        Mockito.verify(itemRepo, times(1)).findById(1L);
    }

    @Test
    public void get_item_by_name_happy_path() {
        Item item1 = TestUtils.createItem(1L, "Item 1", "Description 1", BigDecimal.TEN);

        when(itemRepo.findByName("Item 1")).thenReturn(Arrays.asList(item1));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Item 1");
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Description 1", items.get(0).getDescription());
        assertEquals(BigDecimal.TEN, items.get(0).getPrice());
        Mockito.verify(itemRepo, times(1)).findByName("Item 1");
    }

    @Test
    public void get_item_by_id_with_invalid_name() {
        when(itemRepo.findByName(Mockito.anyString())).thenReturn(Lists.emptyList());

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Item 1");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        Mockito.verify(itemRepo, times(1)).findByName(Mockito.anyString());
    }
}
