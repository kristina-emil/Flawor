package com.flavor.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeycloakAuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakAuthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Получаем роли пользователя
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Логируем роли
        logger.info("Пользователь {} вошел в систему с ролями: {}", authentication.getName(), roles);

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/category");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user/index");
        } else {
            response.sendRedirect("/access-denied"); // Если у пользователя нет подходящей роли
        }
    }
}

