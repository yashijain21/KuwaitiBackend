package com.waffleandhshake.Controller;



import com.waffleandhshake.DTO.MenuItemRequest;
import com.waffleandhshake.Entity.MenuItem;
import com.waffleandhshake.Service.MenuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItem> allItems() {
        return menuService.getAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable Long categoryId) {
        return menuService.getMenuItemsByCategory(categoryId);
    }


    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem addItem(
            @RequestPart("data") MenuItemRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file // optional
    ) throws IOException {

        if (file != null && !file.isEmpty()) {
            // Ensure upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create unique file name
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Save the file
            Files.write(filePath, file.getBytes());

            // Set image path for static access
            request.setImagePath("/uploads/" + fileName);
        }

        // Save item in DB (imagePath may be null)
        return menuService.save(request);
    }


    @DeleteMapping("/admin/{id}")
    public void deleteItem(@PathVariable Long id) {
        menuService.delete(id);
    }

    @PutMapping(value = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem updateItem(
            @PathVariable Long id,
            @RequestPart("data") MenuItemRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());
            request.setImagePath("/uploads/" + fileName);
        }

        return menuService.update(id, request);
    }


    // ✅ Partial update with optional image
    @PatchMapping(value = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem patchItem(
            @PathVariable Long id,
            @RequestPart("updates") Map<String, Object> updates,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return menuService.partialUpdateWithImage(id, updates, file, uploadDir);
    }


}

