package com.waffleandhshake.Service;


import com.waffleandhshake.Entity.Gallery;
import com.waffleandhshake.Repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public List<Gallery> getAll() {
        return galleryRepository.findAll();
    }

    public Gallery add(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    public void delete(Long id) {
        Optional<Gallery> galleryOptional = galleryRepository.findById(id);
        if (galleryOptional.isPresent()) {
            Gallery gallery = galleryOptional.get();

            // Convert stored URL path (/uploads/filename.jpg) → actual local path
            String relativePath = gallery.getImagePath().replace("/uploads/", "");
            File file = new File(uploadDir, relativePath);

            // Delete the file from filesystem if it exists
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("⚠️ Failed to delete file: " + file.getAbsolutePath());
                }
            }

            // Remove the database record
            galleryRepository.deleteById(id);
        }
    }
}
