package com.flavor.service;

import com.flavor.model.MealResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MealService {

    private final WebClient webClient;

    public MealService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.themealdb.com/api/json/v1/1").build();
    }

    // Получение блюда по ID
    public Mono<MealResponse> getMealById(String id) {
        return webClient.get()
                .uri("/lookup.php?i=" + id)
                .retrieve()
                .bodyToMono(MealResponse.class);
    }

    // Получение случайного блюда
    public Mono<MealResponse> getRandomMeal() {
        return webClient.get()
                .uri("/random.php")
                .retrieve()
                .bodyToMono(MealResponse.class);
    }

    // Поиск блюда по названию
    public Mono<MealResponse> searchMealByName(String name) {
        return webClient.get()
                .uri("/search.php?s=" + name)
                .retrieve()
                .bodyToMono(MealResponse.class);
    }
}