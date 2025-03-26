package com.flavor.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component // Добавляем аннотацию Component
public class KeycloakAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Получаем имя пользователя
        String username = authentication.getName();

        // Получаем email из аутентификации (может быть в "preferred_username" или "email")
        String email = (String) authentication.getCredentials();

        // Пример: если email или имя пользователя "admin", перенаправляем на админку
        if ("admin".equals(username) || "kristina-emil@mail.ru".equals(email)) {
            response.sendRedirect("/admin/category");
        } else {
            // Для всех остальных пользователей перенаправляем на стандартную страницу
            response.sendRedirect("/user/index");
        }
    }
}
