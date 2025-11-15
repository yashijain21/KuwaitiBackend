package com.waffleandhshake.Repository;


import com.waffleandhshake.Entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
