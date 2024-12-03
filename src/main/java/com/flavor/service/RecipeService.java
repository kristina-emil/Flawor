package com.flavor.service;

import com.flavor.model.Recipe;
import com.flavor.model.Category;
import com.flavor.repository.RecipeRepository;
import com.flavor.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    public Recipe addRecipe(Recipe recipe) {
        if (recipe.getCategory() == null || recipe.getCategory().getId() == null) {
            throw new RuntimeException("Категория не указана или некорректна.");
        }

        Long categoryId = recipe.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категория с ID " + categoryId + " не найдена"));

        recipe.setCategory(category);
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> getRecipesWithLimitOffset(int offset, int limit) {
        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.findAllByOrderByIdAsc(pageable);
    }
}
