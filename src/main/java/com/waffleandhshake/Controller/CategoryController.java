package com.waffleandhshake.Controller;


import com.waffleandhshake.DTO.CategoryWithItemsDTO;
import com.waffleandhshake.Entity.Category;
import com.waffleandhshake.Service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PostMapping("/admin")
    public Category add(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/admin/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @GetMapping("/with-items")
    public List<CategoryWithItemsDTO> getAllCategoriesWithItems() {
        List<Category> categories = categoryService.getAll();
        return categories.stream()
                .map(CategoryWithItemsDTO::new)
                .collect(Collectors.toList());
    }

}
