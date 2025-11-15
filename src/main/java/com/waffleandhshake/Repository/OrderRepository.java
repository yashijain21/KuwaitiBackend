package com.waffleandhshake.Repository;

import com.waffleandhshake.Entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM orders_items WHERE items_id IN (SELECT id FROM cart_item WHERE menu_item_id = :menuItemId)", nativeQuery = true)
    void deleteOrderItemsByMenuItemId(Long menuItemId);

}
