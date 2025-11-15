package com.waffleandhshake.Controller;

import com.waffleandhshake.Entity.Order;
import com.waffleandhshake.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Public endpoint for placing orders
    @PostMapping
    public ResponseEntity<?> place(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.placeOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            // Return Arabic error message
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("message", "حدث خطأ أثناء إرسال الطلب"));
        }
    }

    // Admin-only endpoint to fetch all orders
    @GetMapping("/admin")
    public ResponseEntity<?> getAll() {
        try {
            List<Order> orders = orderService.allOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("message", "حدث خطأ أثناء جلب الطلبات"));
        }
    }

    // Admin endpoint to update order status
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "الرجاء تحديد الحالة الجديدة"));
        }

        try {
            Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("message", "حدث خطأ أثناء تحديث حالة الطلب"));
        }
    }
}
