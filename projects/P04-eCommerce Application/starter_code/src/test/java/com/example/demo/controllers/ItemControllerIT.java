package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.net.URI;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ItemControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void getItems_http_ok() throws Exception {
        //given
        Item item = getItem();
        item.setName("name_getItems");
        itemRepository.save(item);

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/item"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions
                .andExpect(jsonPath("$.[?(@.name == 'name_getItems')].description", contains(item.getDescription())))
                .andExpect(jsonPath("$.[?(@.name == 'name_getItems')].price", contains(item.getPrice().doubleValue())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getItems_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/item")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getItemById_http_ok() throws Exception {
        //given
        Item item = itemRepository.save(getItem());
        int id = Math.toIntExact(item.getId());

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/item/" + id))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())))
                .andExpect(jsonPath("$.price", is(item.getPrice().doubleValue())));
    }

    @Test
    public void getItemById_http_not_found() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/item/100001"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getItemById_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/item/10001")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getItemsByName_http_ok() throws Exception {
        //given
        Item item = getItem();
        item.setName("valid_item_name");
        Item saveItem = itemRepository.save(getItem());
        int id = Math.toIntExact(saveItem.getId());

        //when
        ResultActions resultActions = mvc.perform(
                get(new URI("/api/item/name/" + saveItem.getName()))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isOk());

        //then
        resultActions
                .andExpect(jsonPath("$.*.id", contains(id)))
                .andExpect(jsonPath("$.*.name", contains(saveItem.getName())))
                .andExpect(jsonPath("$.*.description", contains(saveItem.getDescription())))
                .andExpect(jsonPath("$.*.price", contains(saveItem.getPrice().doubleValue())));
    }

    @Test
    public void getItemsByName_http_not_found() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/item/name/invalid_item_name"))
                        .header("Authorization", getValidJwtToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getItemsByName_http_forbidden() throws Exception {
        //expect
        mvc.perform(
                get(new URI("/api/item/invalid_item_name")))
                .andExpect(status().isForbidden());
    }

    private String getValidJwtToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjg3NTIwNzk1fQ.Pcef87avJHpAu6ZN84yiJYPxz2NNcSyAdcerfaKEua2rJpBNMZDVoU4gi_-F5W0rDwyy3ndcTn6964WCl6W3Gw";
    }

    private Item getItem() {
        Item item = new Item();
        item.setName("itemName");
        item.setDescription("itemDescription");
        item.setPrice(BigDecimal.valueOf(11));

        return item;
    }
}
