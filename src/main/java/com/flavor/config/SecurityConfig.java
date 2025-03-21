package com.flavor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/public/**").permitAll() // Разрешить доступ к публичным ресурсам без аутентификации
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
            )
            .oauth2Login() // Настройка OAuth2 авторизации через Keycloak
                .loginPage("/oauth2/authorization/keycloak") // Явно указываем на страницу входа Keycloak
                .defaultSuccessUrl("/user/index", true)  // Перенаправление на /home после успешного входа
                .failureUrl("/login?error=true")  // Перенаправление при ошибке аутентификации
            .and()
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder()) // Используем настроенный JwtDecoder
                    .jwtAuthenticationConverter(new JwtToAuthenticationConverter()) // Конвертируем JWT в аутентификацию
                )
            );
        return http.build();
    }

    // Конвертер для преобразования JWT в аутентификацию
    private static class JwtToAuthenticationConverter implements org.springframework.core.convert.converter.Converter<Jwt, AbstractAuthenticationToken> {
        @Override
        public AbstractAuthenticationToken convert(Jwt jwt) {
            String username = jwt.getClaimAsString("preferred_username");
            return new UsernamePasswordAuthenticationToken(username, "", null); // Роли можно добавить, если они есть
        }
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "http://localhost:8081/realms/flavor_realm/protocol/openid-connect/certs"; // Замените URL на ваш сервер Keycloak
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistration.withRegistrationId("keycloak")
                .clientId("flavor-app")
                .clientSecret("esfj0yKbKDH4aocufbcZGgJuDVi8c2lL")
                .scope("openid")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8081/realms/flavor-realm/protocol/openid-connect/userinfo")
                .redirectUri("http://localhost:8081/login/oauth2/code/keycloak")  // Это URI для получения авторизационного кода
                .clientName("Keycloak")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Используем BCryptPasswordEncoder
    }
}

