package com.flavor.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.flavor.importer.Importer;
import java.util.List;

@Component
public class ImportRunner implements CommandLineRunner {

    private final List<Importer> importers;  // Список всех сервисов импорта

    @Autowired
    public ImportRunner(List<Importer> importers) {
        this.importers = importers;
    }

    @Override
    public void run(String... args) {
        // Проходим по всем импортерам и вызываем importAllRecipes()
        importers.forEach(importer -> {
            System.out.println("🔄 Начинаем импорт с сервиса: " + importer.getClass().getSimpleName());
            importer.importAllRecipes();
        });
    }
}
