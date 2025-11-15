package com.waffleandhshake.Repository;


import com.waffleandhshake.Entity.CartItem;
import com.waffleandhshake.Entity.MenuItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.menuItem.id = :menuItemId")
    void deleteByMenuItemId(Long menuItemId);

    void deleteByMenuItem(MenuItem item);
}

