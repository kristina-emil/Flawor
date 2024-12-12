package com.flavor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/category")
    public String categoryPage() {
        return "category";
    }
}
