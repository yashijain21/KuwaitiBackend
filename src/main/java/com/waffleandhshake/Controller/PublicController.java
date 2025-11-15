package com.waffleandhshake.Controller;

import com.waffleandhshake.DTO.CategoryWithItemsDTO;
import com.waffleandhshake.Entity.Banner;
import com.waffleandhshake.Entity.Category;
import com.waffleandhshake.Entity.Gallery;
import com.waffleandhshake.Entity.Section;
import com.waffleandhshake.Service.BannerService;
import com.waffleandhshake.Service.CategoryService;
import com.waffleandhshake.Service.GalleryService;
import com.waffleandhshake.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private GalleryService galleryService;



    private final CategoryService categoryService;

    public PublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/banner")
    public List<Banner> getAllBanners() {
        return bannerService.getAll();
    }

    // READ ONE
    @GetMapping("/api/banner/{id}")
    public Banner getBannerById(@PathVariable Long id) {
        return bannerService.getById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id " + id));
    }



    @GetMapping("/api/categories/with-items")
    public List<CategoryWithItemsDTO> getAllCategoriesWithItems() {
        List<Category> categories = categoryService.getAll();
        return categories.stream()
                .map(CategoryWithItemsDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/sections")
    public List<Section> getAllSections() {
        return sectionService.getAll();
    }

    @GetMapping("/api/gallery")
    public List<Gallery> getAll() {
        return galleryService.getAll();
    }
}
