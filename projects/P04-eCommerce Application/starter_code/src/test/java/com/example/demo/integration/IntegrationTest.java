package com.example.demo.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void query_for_user_needs_authentication() throws Exception {
        mvc.perform(get("/api/user/test")).andExpect(status().isForbidden());
    }

    @Test
    public void create_user_then_login_and_query_for_user_with_jwt_token() throws Exception {
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        mvc.perform(
                post("/api/user/create").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(r)))
                .andExpect(status().isOk());

        ObjectNode loginRequest = mapper.createObjectNode();
        loginRequest.put("username", "test");
        loginRequest.put("password", "testPassword");
        RequestBuilder requestBuilder = post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest.toString());

        MvcResult loginResult = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        assertNotNull(loginResult);
        String accessToken = loginResult.getResponse().getHeader(SecurityConstants.HEADER_STRING);

        MvcResult result = mvc.perform(get("/api/user/{username}", "test")
                .header("Authorization", "Bearer " + accessToken).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        User user = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(r.getUsername(), user.getUsername());
    }
}
