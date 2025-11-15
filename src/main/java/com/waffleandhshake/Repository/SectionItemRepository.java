package com.waffleandhshake.Repository;

import com.waffleandhshake.Entity.SectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionItemRepository extends JpaRepository<SectionItem, Long> {
    List<SectionItem> findBySectionId(Long sectionId);
}
