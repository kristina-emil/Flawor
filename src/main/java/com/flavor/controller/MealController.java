package com.flavor.controller;

import com.flavor.model.MealResponse;
import com.flavor.service.MealService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/{id}")
    public Mono<MealResponse> getMealById(@PathVariable String id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/random")
    public Mono<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @GetMapping("/search")
    public Mono<MealResponse> searchMealByName(@RequestParam String name) {
        return mealService.searchMealByName(name);
    }
}
