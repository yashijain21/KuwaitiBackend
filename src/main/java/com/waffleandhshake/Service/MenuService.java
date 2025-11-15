package com.waffleandhshake.Service;

import com.waffleandhshake.DTO.MenuItemRequest;
import com.waffleandhshake.Entity.Category;
import com.waffleandhshake.Entity.MenuItem;
import com.waffleandhshake.Repository.CartItemRepository;
import com.waffleandhshake.Repository.CategoryRepository;
import com.waffleandhshake.Repository.MenuItemRepository;
import com.waffleandhshake.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class MenuService {

    private final MenuItemRepository menuRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderItemRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<MenuItem> getMenuItemsByCategory(Long categoryId) {
        return menuRepo.findByCategoryId(categoryId);
    }


    public MenuService(MenuItemRepository menuRepo, CartItemRepository cartItemRepo, OrderRepository orderItemRepo) {
        this.menuRepo = menuRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderItemRepo = orderItemRepo;
    }

    // ✅ Get all menu items
    public List<MenuItem> getAll() {
        return menuRepo.findAll();
    }

    // ✅ Save a new menu item
    public MenuItem save(MenuItemRequest request) {
        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setIngredients(request.getIngredients());
        item.setImagePath(request.getImagePath());

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        item.setCategory(category);

        return menuRepo.save(item);
    }

    // ✅ Delete a menu item
    public void delete(Long id) {
        orderItemRepo.deleteOrderItemsByMenuItemId(id);
        cartItemRepo.deleteByMenuItemId(id);
        menuRepo.deleteById(id);
    }

    // ✅ Full update of menu item
    public MenuItem update(Long id, MenuItemRequest request) {
        return menuRepo.findById(id)
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setPrice(request.getPrice());
                    existing.setIngredients(request.getIngredients());
                    existing.setImagePath(request.getImagePath());

                    Category category = categoryRepo.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    existing.setCategory(category);

                    return menuRepo.save(existing);
                })
                .orElseThrow(() -> new NoSuchElementException("MenuItem not found with ID: " + id));
    }

    // ✅ Partial update with optional image
    public MenuItem partialUpdateWithImage(Long id, Map<String, Object> updates, MultipartFile file, String uploadDir) throws IOException {
        MenuItem existingItem = menuRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found"));

        updates.forEach((key, value) -> {
            if (value == null) return;
            switch (key) {
                case "name" -> existingItem.setName((String) value);
                case "price" -> existingItem.setPrice(Double.parseDouble(value.toString()));
                case "ingredients" -> existingItem.setIngredients((String) value);
                case "categoryId" -> {
                    Long categoryId = Long.parseLong(value.toString());
                    Category category = categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    existingItem.setCategory(category);
                }
            }
        });

        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, file.getBytes());

            existingItem.setImagePath("/uploads/" + filename);
        }

        return menuRepo.save(existingItem);
    }
}
