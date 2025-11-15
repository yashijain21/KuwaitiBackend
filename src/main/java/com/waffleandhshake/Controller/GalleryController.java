package com.waffleandhshake.Controller;

import com.waffleandhshake.Entity.Gallery;
import com.waffleandhshake.Service.GalleryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public List<Gallery> getAll() {
        return galleryService.getAll();
    }

    @PostMapping("/admin")
    public Gallery addImage(
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        // Ensure upload directory exists
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) Files.createDirectories(path);

        // Save file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // Build entity
        Gallery gallery = new Gallery();
        gallery.setImagePath("/uploads/" + fileName);

        // Save via service
        return galleryService.add(gallery);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteImage(@PathVariable Long id) {
        galleryService.delete(id);
    }
}
