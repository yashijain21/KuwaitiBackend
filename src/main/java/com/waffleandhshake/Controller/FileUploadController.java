package com.waffleandhshake.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return "/uploads/" + fileName;
    }

    @GetMapping("/images")
    public List<String> listImages() throws IOException {
        File folder = new File(uploadDir);
        File[] files = folder.listFiles();
        List<String> imagePaths = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imagePaths.add("/uploads/" + file.getName());
                }
            }
        }

        return imagePaths;
    }

}
