package com.flavor.controller;

import com.flavor.model.Category;
import com.flavor.model.Recipe;
import com.flavor.service.CategoryService;
import com.flavor.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
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
        System.out.println("Получен рецепт: " + recipe);  // Логирование
        if (recipe.getCategory() == null || recipe.getCategory().getId() == null) {
            throw new RuntimeException("Не указана категория.");
        }

        Optional<Category> category = categoryService.getCategoryById(recipe.getCategory().getId());
        if (category.isEmpty()) {
            throw new RuntimeException("Категория не найдена.");
        }

        recipe.setCategory(category.get());
        return recipeService.addRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/paginated")
    public List<Recipe> getRecipesWithPagination(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit
    ) {
        return recipeService.getRecipesWithLimitOffset(offset, limit);
    }
}
