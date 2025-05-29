package com.github.daymoon;

import java.time.LocalDateTime;

public class Purchase {
    
    private LocalDateTime purchaseDate;
    private int amount;
    private MarketUser buyer;
    private MarketUser seller;
    private Product product;
    public Purchase(int amount , MarketUser buyer, MarketUser seller , Product product){
        this.purchaseDate = purchaseDate.now();
        this.amount = amount;
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;

        seller.totalProductsSelled++;
        buyer.addToPurchaseHistory(product);
    }

}
