package com.flavor.importer;

import com.flavor.dto.MealDto;
import com.flavor.model.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.flavor.repository.CategoryRepository;
import com.flavor.repository.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeImporter implements Importer {

    private final RestTemplate restTemplate = new RestTemplate();
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeMapper mapper;

    public RecipeImporter(RecipeRepository recipeRepository,
                          CategoryRepository categoryRepository,
                          RecipeMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public void importAllRecipes() {
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";
        Set<String> existingRecipeExternalIds = new HashSet<>();

        // Загружаем все рецепты из базы данных для проверки внешнего ID
        recipeRepository.findAll().forEach(recipe -> {
            existingRecipeExternalIds.add(recipe.getExternalId());
        });

        int importedCount = 0;
        int totalRecipes = 10;  // Загружаем 10 рецептов

        // Загружаем рецепты из API
        for (int i = 0; i < totalRecipes; i++) {
            try {
                MealDto response = restTemplate.getForObject(url, MealDto.class);

                if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
                    for (var meal : response.getMeals()) {
                        Recipe recipe = mapper.map(meal, categoryRepository);

                        // Проверка, если рецепт с таким внешним ID уже существует в базе данных
                        if (existingRecipeExternalIds.contains(recipe.getExternalId())) {
                            System.out.println("⏩ Рецепт с таким внешним ID уже существует: " + recipe.getName());
                            continue;  // Пропускаем этот рецепт
                        }

                        // Сохраняем новый рецепт в базу данных
                        recipeRepository.save(recipe);
                        existingRecipeExternalIds.add(recipe.getExternalId());
                        System.out.println("✅ Импортирован новый рецепт: " + recipe.getName());
                        importedCount++;
                    }
                }

                Thread.sleep(500); // Задержка между запросами

            } catch (Exception e) {
                System.err.println("⚠️ Ошибка при импорте рецепта: " + e.getMessage());
            }
        }

        System.out.println("✅ Импорт завершён. Импортировано новых рецептов: " + importedCount);
    }
}
