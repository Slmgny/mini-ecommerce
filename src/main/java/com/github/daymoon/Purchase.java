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

    
}

