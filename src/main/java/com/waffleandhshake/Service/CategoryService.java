package com.waffleandhshake.Service;



import com.waffleandhshake.Entity.Category;
import com.waffleandhshake.Entity.MenuItem;
import com.waffleandhshake.Repository.CartItemRepository;
import com.waffleandhshake.Repository.CategoryRepository;
import com.waffleandhshake.Repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Autowired
    private MenuItemRepository menuItemRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        List<MenuItem> items = category.getItems();  // ✅ This gives you real MenuItem entities
        if (!items.isEmpty()) {
            // Step 1: Delete cart items linked to each menu item
            for (MenuItem item : items) {
                cartItemRepo.deleteByMenuItem(item);  // ✅ Make sure 'item' is a MenuItem, not MenuItemResponse
            }

            // Step 2: Delete all menu items
            menuItemRepo.deleteAll(items);
        }

        // Step 3: Delete the category
        categoryRepo.delete(category);
    }



    public Category update(Long id, Category updatedCategory) {
        return categoryRepo.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    return categoryRepo.save(existingCategory);
                })
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + id));
    }

}

