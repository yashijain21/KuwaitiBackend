package com.waffleandhshake.Entity;

import jakarta.persistence.*;

@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    public Gallery() {}

    public Gallery(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
