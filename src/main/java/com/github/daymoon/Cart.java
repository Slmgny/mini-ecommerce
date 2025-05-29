package com.github.daymoon;

public class Cart {
    private int amount;
    private int buyerId;
    private int productId;


    public Cart(int buyerId, int productId, int amount) {
        this.amount = amount;
        this.buyerId = buyerId;
        this.productId = productId;


    }

}
