package com.waffleandhshake.Service;

import com.waffleandhshake.DTO.BannerRequest;
import com.waffleandhshake.Entity.Banner;
import com.waffleandhshake.Repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BannerRepository bannerRepository;

    public Banner save(BannerRequest request) {
        Banner banner = new Banner();
        banner.setTitle(request.getTitle());
        banner.setSubtitle(request.getSubtitle());
        banner.setDescription(request.getDescription());
        banner.setImagePath(request.getImagePath());
        return bannerRepository.save(banner);
    }

    public List<Banner> getAll() {
        return bannerRepository.findAll();
    }

    public Optional<Banner> getById(Long id) {
        return bannerRepository.findById(id);
    }

    public Banner update(Long id, BannerRequest request) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id " + id));

        banner.setTitle(request.getTitle());
        banner.setSubtitle(request.getSubtitle());
        banner.setDescription(request.getDescription());
        if (request.getImagePath() != null) {
            banner.setImagePath(request.getImagePath());
        }

        return bannerRepository.save(banner);
    }

    public void delete(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id " + id));

        // Delete image file if exists
        if (banner.getImagePath() != null) {
            Path imagePath = Paths.get(uploadDir, banner.getImagePath().replace("/uploads/", ""));
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.err.println("Failed to delete image: " + e.getMessage());
            }
        }

        // Delete banner from DB
        bannerRepository.deleteById(id);
    }

}
