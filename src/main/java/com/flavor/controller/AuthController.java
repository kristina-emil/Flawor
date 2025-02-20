package com.flavor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Principal principal) {
        System.out.println("User: " + principal.getName());
        return "home"; // Перенаправление на домашнюю страницу после успешной аутентификации
    }
}
