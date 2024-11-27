package com.flavor.controller;

import com.flavor.model.Recipe;
import com.flavor.service.RecipeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getRecipe(@PathVariable Long id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    /**
     * Эндпоинт для получения рецептов с пагинацией.
     *
     * @param offset количество записей, которые нужно пропустить.
     * @param limit  количество записей, которые нужно вернуть.
     * @return список рецептов.
     */
    @GetMapping("/paginated")
    public List<Recipe> getRecipesWithPagination(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit
    ) {
        return recipeService.getRecipesWithLimitOffset(offset, limit);
    }
}
