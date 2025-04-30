package com.flavor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")  // Указываем явно тип text
    private String name;

    @Column(columnDefinition = "text")  // Указываем явно тип text
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(unique = true)  // Чтобы не было дубликатов по внешнему ID
    private String externalId;

    @Column(nullable = false)
    private Integer systemId;

    private String imageUrl;

    // Конструктор по умолчанию
    public Recipe() {
    }

    // Конструктор с externalId и systemId
    public Recipe(String name, String description, Category category, String externalId, Integer systemId) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.externalId = externalId;
        this.systemId = systemId;
    }

    // Конструктор без externalId и systemId
    public Recipe(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getter и Setter методы
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("categoryId")
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}
