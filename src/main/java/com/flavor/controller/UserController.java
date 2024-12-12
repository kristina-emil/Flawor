package com.flavor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/index")
    public String indexPage() {
        return "index";
    }
}
