package com.waffleandhshake.Controller;

import com.waffleandhshake.DTO.BannerRequest;
import com.waffleandhshake.Entity.Banner;
import com.waffleandhshake.Service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BannerService bannerService;

    // CREATE
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Banner addBanner(
            @RequestPart("data") BannerRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());
            request.setImagePath("/uploads/" + fileName);
        }

        return bannerService.save(request);
    }

    // READ ALL
    @GetMapping
    public List<Banner> getAllBanners() {
        return bannerService.getAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public Banner getBannerById(@PathVariable Long id) {
        return bannerService.getById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id " + id));
    }

    // UPDATE
    @PutMapping(value = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Banner updateBanner(
            @PathVariable Long id,
            @RequestPart("data") BannerRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());
            request.setImagePath("/uploads/" + fileName);
        }

        return bannerService.update(id, request);
    }

    // DELETE
    @DeleteMapping("/admin/{id}")
    public String deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return "Banner deleted successfully with id " + id;
    }
}
