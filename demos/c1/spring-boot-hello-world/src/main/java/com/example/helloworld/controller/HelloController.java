package com.example.helloworld.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    private Map<String, Object> result = new HashMap<>();

    @RequestMapping("/hello")
    public Map<String, Object> hello() {
        result.put("name", "Stephen");
        result.put("city", "San Jose");
        return result;
    }
}
