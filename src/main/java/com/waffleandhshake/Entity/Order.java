package com.waffleandhshake.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;         // ✅ New field
    private String phoneNumber;   // ✅ New field

    private String tableNo;
    private LocalDateTime orderTime;
    private double totalAmount;

    private String status; // PLACED, READY, SERVED

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items;

    public Order() {
    }

    public Order(Long id, String customerName, String email, String phoneNumber, String tableNo,
                 LocalDateTime orderTime, double totalAmount, String status, List<CartItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tableNo = tableNo;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {                      // ✅ Getter
        return email;
    }

    public void setEmail(String email) {            // ✅ Setter
        this.email = email;
    }

    public String getPhoneNumber() {                // ✅ Getter
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {// ✅ Setter
        this.phoneNumber = phoneNumber;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
