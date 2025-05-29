package com.github.daymoon;

public class Cart {
    private int amount;
    private MarketUser buyer;
    private Product product;


    public Cart(int amount, MarketUser buyer, Product product) {
        this.amount = amount;
        this.buyer = buyer;
        this.product = product;

        
    }

}
