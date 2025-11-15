package com.waffleandhshake.Controller;


import com.waffleandhshake.DTO.SectionItemRequest;
import com.waffleandhshake.Entity.SectionItem;
import com.waffleandhshake.Service.SectionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/section-items")
public class SectionItemController {

    @Autowired
    private SectionItemService itemService;

    @Value("${file.upload-dir}")
    private String uploadDir;



    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SectionItem addItem(@RequestPart("data") SectionItemRequest request,
                               @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) Files.createDirectories(path);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);
            Files.write(filePath, file.getBytes());
            request.setImagePath("/uploads/" + fileName);
        }
        return itemService.save(request);
    }

    @PutMapping(value = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SectionItem updateItem(@PathVariable Long id,
                                  @RequestPart("data") SectionItemRequest request,
                                  @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) Files.createDirectories(path);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);
            Files.write(filePath, file.getBytes());
            request.setImagePath("/uploads/" + fileName);
        }
        return itemService.update(id, request);
    }

    @DeleteMapping("/admin/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return "Item deleted successfully";
    }

    @GetMapping("/section/{sectionId}")
    public List<SectionItem> getItemsBySection(@PathVariable Long sectionId) {
        return itemService.getBySection(sectionId);
    }

    @GetMapping
    public List<SectionItem> getAllItems() {
        return itemService.getAll();
    }

}
