package com.flavor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Разрешаем CORS для всех путей
                .allowedOrigins("http://127.0.0.1:5500")  // Разрешаем доступ только с фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Разрешаем методы GET, POST и другие
                .allowedHeaders("*")  // Разрешаем все заголовки
                .allowCredentials(true);
    }
}
