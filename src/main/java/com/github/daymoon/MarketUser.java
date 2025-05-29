package com.github.daymoon;

import java.util.ArrayList;

public class MarketUser extends User {

    private ArrayList<Product> purchaseHistory = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<Product> marketProducts = new ArrayList<>();

    public MarketUser(String name , String password){
        super(name , password);
    }

    public void addToCart(Product p){
        cart.add(p);
    }

    public void addToMarket(Product p){
        marketProducts.add(p);
    }

    public void addToPurchaseHistory(Product p){
        purchaseHistory.add(p);
    }

    public void ShowCart(){
        for(Product p: cart){
            System.out.println(p.getName() + "                " + p.getPrice());
        }
    }

}
