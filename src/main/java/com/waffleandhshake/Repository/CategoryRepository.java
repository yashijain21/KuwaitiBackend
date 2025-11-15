package com.waffleandhshake.Repository;

import com.waffleandhshake.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
