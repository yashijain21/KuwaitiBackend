package com.waffleandhshake.Controller;

import com.waffleandhshake.DTO.SectionRequest;
import com.waffleandhshake.Entity.Section;
import com.waffleandhshake.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Section addSection(
            @RequestPart("data") SectionRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        // ✅ Generate a key automatically based on the title
        String generatedKey = generateKeyFromTitle(request.getTitle());
        request.setKey(generatedKey);

        return sectionService.save(request);
    }

    /**
     * Generates a key by converting title to lowercase and replacing spaces/special chars.
     * Example: "Featured Waffles" -> "featured-waffles"
     */
    private String generateKeyFromTitle(String title) {
        if (title == null) return "";
        return title
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // remove special characters
                .replaceAll("\\s+", "-");        // replace spaces with hyphens
    }


    @PutMapping(value = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Section updateSection(@PathVariable Long id,
                                 @RequestPart("data") SectionRequest request,
                                 @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) Files.createDirectories(path);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);
            Files.write(filePath, file.getBytes());
        }
        return sectionService.update(id, request);
    }

    @DeleteMapping("/admin/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.delete(id);
        return "Section deleted successfully";
    }

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAll();
    }

    @GetMapping("/{key}")
    public Section getSectionByKey(@PathVariable String key) {
        return sectionService.getByKey(key);
    }
}
