document.addEventListener("DOMContentLoaded", () => {
    const categoryTableBody = document.querySelector("#category-table tbody");
    const addCategoryBtn = document.getElementById("add-category");

    // API URL для категорий
    const API_URL_CATEGORIES = 'http://localhost:8080/categories';

    // Функция для загрузки категорий
    const loadCategories = async () => {
        try {
            const response = await fetch(API_URL_CATEGORIES);
            const categories = await response.json();
            categoryTableBody.innerHTML = ''; // Очистить таблицу
            categories.forEach((category, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${category.name}</td>
                    <td>
                        <button class="delete-btn" data-id="${category.id}">Удалить</button>
                    </td>
                `;
                categoryTableBody.appendChild(row);
            });

            // Добавить обработчики удаления
            document.querySelectorAll('.delete-btn').forEach(button => {
                button.addEventListener('click', async (e) => {
                    const id = e.target.dataset.id;
                    await fetch(`${API_URL_CATEGORIES}/${id}`, { method: 'DELETE' });
                    loadCategories(); // Перезагрузить категории
                });
            });
        } catch (err) {
            console.error("Ошибка загрузки категорий:", err);
        }
    };

    // Добавление новой категории
    addCategoryBtn.addEventListener("click", async () => {
        const categoryName = prompt("Введите название категории:");
        if (categoryName) {
            await fetch(API_URL_CATEGORIES, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: categoryName }),
            });
            loadCategories(); // Перезагрузить категории
        }
    });

    // Загрузить категории при загрузке страницы
    loadCategories();
});
