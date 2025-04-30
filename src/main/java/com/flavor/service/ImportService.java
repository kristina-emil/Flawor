package com.flavor.service;

import com.flavor.importer.Importer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportService {

    private final List<Importer> importers;

    @Autowired
    public ImportService(List<Importer> importers) {
        this.importers = importers;
    }

    public void runImport() {
        importers.forEach(importer -> {
            System.out.println("🔄 Начинаем импорт с сервиса: " + importer.getClass().getSimpleName());
            importer.importAllRecipes();
        });
    }
}
