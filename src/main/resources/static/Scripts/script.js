document.addEventListener("DOMContentLoaded", () => {
    const viewRecipesBtn = document.getElementById("view-recipes");
    const createRecipeBtn = document.getElementById("create-recipe");
    const recipeTableContainer = document.getElementById("recipe-table-container");
    const recipeTableBody = document.querySelector(".recipe-table tbody");
  
    // API URL
    const API_URL = 'http://localhost:8080/recipes';
  
    // Fetch and display recipes
    const loadRecipes = async () => {
      try {
        const response = await fetch(API_URL);
        const recipes = await response.json();
        recipeTableBody.innerHTML = ''; // Clear table
        recipes.forEach((recipe, index) => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${index + 1}</td>
            <td>${recipe.name}</td>
            <td>${recipe.description}</td>
            <td>
              <button class="edit-btn" data-id="${recipe.id}">Изменить</button>
              <button class="delete-btn" data-id="${recipe.id}">Удалить</button>
            </td>
          `;
          recipeTableBody.appendChild(row);
        });
  
        // Add event listeners to delete buttons
        document.querySelectorAll('.delete-btn').forEach(button => {
          button.addEventListener('click', async (e) => {
            const id = e.target.dataset.id;
            await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            loadRecipes(); // Refresh table
          });
        });
      } catch (err) {
        console.error('Error loading recipes:', err);
      }
    };
  
    // Toggle visibility of the recipe table
    viewRecipesBtn.addEventListener("click", () => {
      recipeTableContainer.classList.toggle("hidden");
      if (!recipeTableContainer.classList.contains("hidden")) {
        loadRecipes(); // Load recipes when table is visible
      }
    });
  
    // Placeholder for create functionality
    createRecipeBtn.addEventListener("click", () => {
      const name = prompt('Введите название рецепта:');
      const description = prompt('Введите описание рецепта:');
      if (name && description) {
        fetch(API_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ name, description }),
        }).then(() => loadRecipes());
      }
    });
  });
  