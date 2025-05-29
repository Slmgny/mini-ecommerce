package com.github.daymoon;

import java.util.ArrayList;

public class MarketUser extends User {

    protected int totalProductsSelled;
    private ArrayList<Product> purchaseHistory = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<Product> marketProducts = new ArrayList<>();


    

    public int getTotalProductsSelled() {
        return totalProductsSelled;
    }

    public void setTotalProductsSelled(int totalProductsSelled) {
        this.totalProductsSelled = totalProductsSelled;
    }

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
        System.out.printf("%-20s %-10s", "Name", "Price");
        for(Product p: cart){
            System.out.printf("%-20s %-10d", 
            p.getName(), p.getPrice());
        }
    }

    public void ShowUserProducts() {
    
        ProductDAO userProducts = new ProductDAO();
        userProducts.getAllProductsBySellerId(this.id);
    }

    public void ShowPuchaseHistory() {
    
        System.out.printf("%-20s %-10s %-10s %-10s", "Name", "Price", "Amount" , "Purchase Time");
        Product previusProduct = null;
        for (Product p : purchaseHistory) {
            int amount = 1;
            System.out.printf("%-20s %-10d %-10d %-15d%n", 
                p.getName(), p.getPrice() , amount);
            previusProduct = p;
        }
    }
}
