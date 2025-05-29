package com.github.daymoon;

import java.time.LocalDateTime;

public class Purchase {
    private int id;
    private LocalDateTime date;
    private int amount;
    private int buyerId;
    private int sellerId;
    private int productId;

    public Purchase(int amount, int buyerId, int sellerId, int productId) {
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
    }

    public Purchase(int id, String dateString, int amount, int sellerId, int buyerId, int productId) {
        this.id = id;
        this.date = LocalDateTime.parse(dateString);
        this.amount = amount;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    
}

