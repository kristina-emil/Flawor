document.addEventListener("DOMContentLoaded", () => {
    const API_URL_RECIPES = 'http://localhost:8080/recipes';
    const API_URL_CATEGORIES = 'http://localhost:8080/categories';

    const recipeTableBody = document.querySelector(".recipe-table tbody");
    const createRecipeBtn = document.getElementById("create-recipe");
    const viewRecipesBtn = document.getElementById("view-recipes");
    const recipeTableContainer = document.getElementById("recipe-table-container");

    // Загрузка рецептов
    const loadRecipes = async () => {
        try {
            const response = await fetch(API_URL_RECIPES);
            const recipes = await response.json();
            recipeTableBody.innerHTML = ''; // Очистить таблицу
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

            // Добавить обработчики для кнопок удаления
            document.querySelectorAll('.delete-btn').forEach(button => {
                button.addEventListener('click', async (e) => {
                    const id = e.target.dataset.id;
                    await fetch(`${API_URL_RECIPES}/${id}`, { method: 'DELETE' });
                    loadRecipes();
                });
            });
        } catch (err) {
            console.error('Ошибка загрузки рецептов:', err);
        }
    };

    // Создание нового рецепта
    createRecipeBtn.addEventListener("click", async () => {
        const name = prompt('Введите название рецепта:');
        const description = prompt('Введите описание рецепта:');

        // Загрузка категорий
        try {
            const categoryResponse = await fetch(API_URL_CATEGORIES);
            const categories = await categoryResponse.json();

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

            if (name && description) {
                await fetch(API_URL_RECIPES, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        name,
                        description,
                        category: { id: selectedCategory.id }
                    }),
                });
                loadRecipes();
            }
        } catch (err) {
            console.error('Ошибка создания рецепта:', err);
        }
    });

    // Обработчик для кнопки "Посмотреть"
    viewRecipesBtn.addEventListener("click", () => {
        if (recipeTableContainer.classList.contains("hidden")) {
            recipeTableContainer.classList.remove("hidden"); // Показать таблицу рецептов
            loadRecipes(); // Загрузить рецепты
        } else {
            recipeTableContainer.classList.add("hidden"); // Скрыть таблицу рецептов
        }
    });

    // Загрузить данные при загрузке страницы
    loadRecipes();
});