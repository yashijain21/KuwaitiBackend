package com.waffleandhshake.DTO;



import com.waffleandhshake.Entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryWithItemsDTO {
    private Long id;
    private String name;
    private List<MenuItemDTO> items;

    public CategoryWithItemsDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.items = category.getItems()
                .stream()
                .map(MenuItemDTO::new)
                .collect(Collectors.toList());
    }

    // Getters and setters


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

    public List<MenuItemDTO> getItems() {
        return items;
    }

    public void setItems(List<MenuItemDTO> items) {
        this.items = items;
    }
}

