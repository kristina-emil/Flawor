package com.flavor.controller;

import com.flavor.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final ImportService importService;

    @Autowired
    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/run")
    public String runImport() {
        importService.runImport();
        return "Импорт запущен!";
    }
}
