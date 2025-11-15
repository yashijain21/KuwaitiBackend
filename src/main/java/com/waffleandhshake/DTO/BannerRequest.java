package com.waffleandhshake.DTO;

public class BannerRequest {
    private String title;     // e.g., "Premium Milkshakes"
    private String subtitle;  // e.g., "Krämiga & Läckra"
    private String description; // e.g., "Hantverksmässigt tillverkade med de finaste ingredienserna"
    private String imagePath; // will be set after upload

    // getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
