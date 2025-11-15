package com.waffleandhshake.DTO;

import com.waffleandhshake.Entity.MenuItem;

public class MenuItemDTO {
    private Long id;
    private String name;
    private String ingredients;  // ✅ ADD THIS FIELD
    private double price;
    private String imagePath;

    public MenuItemDTO(MenuItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.ingredients = item.getIngredients(); // ✅ ADD THIS LINE
        this.price = item.getPrice();
        this.imagePath = item.getImagePath();
    }

    // ✅ Include getter and setter for ingredients
    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

// (other getters and setters stay same)
}
