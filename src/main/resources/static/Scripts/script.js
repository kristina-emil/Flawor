document.addEventListener("DOMContentLoaded", () => {
  const viewRecipesBtn = document.getElementById("view-recipes");
  const createRecipeBtn = document.getElementById("create-recipe");
  const recipeTableContainer = document.getElementById("recipe-table-container");
  const recipeTableBody = document.querySelector(".recipe-table tbody");

  // API URLs
  const API_URL_RECIPES = 'http://localhost:8080/recipes';
  const API_URL_CATEGORIES = 'http://localhost:8080/categories';

  // Fetch and display recipes
  const loadRecipes = async () => {
      try {
          const response = await fetch(API_URL_RECIPES);
          const recipes = await response.json();
          recipeTableBody.innerHTML = ''; // Clear table
          recipes.forEach((recipe, index) => {
              const row = document.createElement('tr');
              row.innerHTML = `
                  <td>${index + 1}</td>
                  <td>${recipe.name}</td>
                  <td>${recipe.description}</td>
                  <td>${recipe.category?.name || "Без категории"}</td>
                  <td>
                      <button class="delete-btn" data-id="${recipe.id}">Удалить</button>
                  </td>
              `;
              recipeTableBody.appendChild(row);
          });

          // Add event listeners to delete buttons
          document.querySelectorAll('.delete-btn').forEach(button => {
              button.addEventListener('click', async (e) => {
                  const id = e.target.dataset.id;
                  await fetch(`${API_URL_RECIPES}/${id}`, { method: 'DELETE' });
                  loadRecipes(); // Refresh table
              });
          });
      } catch (err) {
          console.error('Error loading recipes:', err);
      }
  };

  // Function to fetch categories
  const loadCategories = async () => {
      try {
          const response = await fetch(API_URL_CATEGORIES);
          return await response.json();
      } catch (err) {
          console.error('Error loading categories:', err);
          return [];
      }
  };

  // Function to create a new recipe
  createRecipeBtn.addEventListener("click", async () => {
      const name = prompt('Введите название рецепта:');
      const description = prompt('Введите описание рецепта:');

      // Fetch categories and display them in a dropdown
      const categories = await loadCategories();
      if (categories.length === 0) {
          alert('Нет доступных категорий. Пожалуйста, добавьте категории перед созданием рецепта.');
          return;
      }

      const categoryOptions = categories.map(category => `${category.id}: ${category.name}`).join('\n');
      const categoryId = prompt(`Выберите категорию, указав её ID:\n${categoryOptions}`);

      const selectedCategory = categories.find(category => category.id === Number(categoryId));
      if (!selectedCategory) {
          alert('Неверный ID категории.');
          return;
      }

      if (name && description && selectedCategory) {
          await fetch(API_URL_RECIPES, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({
                  name,
                  description,
                  categoryId: selectedCategory.id,
              }),
          });
          loadRecipes();
      }
  });

  // Toggle visibility of the recipe table
  viewRecipesBtn.addEventListener("click", () => {
      recipeTableContainer.classList.toggle("hidden");
      if (!recipeTableContainer.classList.contains("hidden")) {
          loadRecipes(); // Load recipes when table is visible
      }
  });

  // Load initial data
  loadRecipes();
});
