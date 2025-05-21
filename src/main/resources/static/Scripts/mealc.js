const API_URL = "http://localhost:8080/meals"; // Адрес твоего Spring Boot сервера

async function fetchRandomMeal() {
    updateUI("Загрузка...");
    try {
        const response = await fetch(`${API_URL}/random`, { credentials: "include" });
        const data = await response.json();
        showMeal(data.meals[0]);
    } catch (error) {
        updateUI("Ошибка загрузки данных 😢");
        console.error("Ошибка:", error);
    }
}

async function searchMeal() {
    const searchQuery = document.getElementById("searchInput").value.trim();
    if (!searchQuery) {
        updateUI("Введите название блюда!");
        return;
    }
    updateUI("Поиск...");
    try {
        const response = await fetch(`${API_URL}/search?name=${searchQuery}`, { credentials: "include" });
        const data = await response.json();
        if (data.meals && data.meals.length > 0) {
            showMeal(data.meals[0]);
        } else {
            updateUI("Ничего не найдено 😔");
        }
    } catch (error) {
        updateUI("Ошибка поиска 😢");
        console.error("Ошибка:", error);
    }
}

function showMeal(meal) {
    const mealContainer = document.getElementById("meal-container");
    mealContainer.innerHTML = `
        <h3>${meal.name}</h3>
        <img src="${meal.thumbnail}" alt="${meal.name}">
        <p><b>Категория:</b> ${meal.category}</p>
        <p><b>Кухня:</b> ${meal.area}</p>
        <p>${meal.instructions.substring(0, 200)}...</p>
        <p><a href="${meal.youtubeUrl}" target="_blank">🎥 Видео-рецепт</a></p>
    `;
}

function updateUI(message) {
    document.getElementById("meal-container").innerHTML = `<p>${message}</p>`;
}
