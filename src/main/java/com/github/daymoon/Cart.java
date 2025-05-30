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


    public int getProductId() {
        return productId;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }
    
}
