package com.example.springbootexception.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {
    @RequestMapping("/roleadd")
    public String add() {
        int num = 10 / 0;
        return "add";
    }
}
