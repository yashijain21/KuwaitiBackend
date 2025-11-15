package com.waffleandhshake.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_key", unique = true) // ✅ fixed
    private String key; // unique identifier for the section, e.g., "specialiteter", "signaturvafflor"

    private String title;
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String description;


    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference   // ✅ prevents infinite recursion
    private List<SectionItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<SectionItem> getItems() {
        return items;
    }

    public void setItems(List<SectionItem> items) {
        this.items = items;
    }
}
