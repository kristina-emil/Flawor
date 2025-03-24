package com.flavor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.flavor.security.KeycloakAuthSuccessHandler;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final KeycloakAuthSuccessHandler keycloakAuthSuccessHandler;

    // Внедрение обработчика успешной аутентификации
    public SecurityConfig(KeycloakAuthSuccessHandler keycloakAuthSuccessHandler) {
        this.keycloakAuthSuccessHandler = keycloakAuthSuccessHandler;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .oauth2Login()
                .successHandler(keycloakAuthSuccessHandler)  // Устанавливаем кастомный обработчик успеха
                .failureUrl("/login?error");
        
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistration.withRegistrationId("keycloak")
                .clientId("flavor-app-id")
                .clientSecret("esfj0yKbKDH4aocufbcZGgJuDVi8c2lL")
                .scope("openid")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/userinfo")
                .jwkSetUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/certs")
                .redirectUri("http://localhost:8080/login/oauth2/code/keycloak")  // URI для получения авторизационного кода
                .issuerUri("http://localhost:8081/realms/flavor-realm")
                .userNameAttributeName("preferred_username")  // Указываем имя атрибута, который будет использоваться как имя пользователя
                .clientName("Keycloak")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Используем BCryptPasswordEncoder
    }
}
