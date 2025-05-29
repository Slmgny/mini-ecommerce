package com.github.daymoon;

import java.util.ArrayList;

public class MarketUser extends User {

    protected int totalProductsSelled;
    private ArrayList<Product> cart = new ArrayList<>();
    private PurchaseDAO purchaseHistory = new PurchaseDAO();
    private ProductDAO userProducts = new ProductDAO();
    

    public int getTotalProductsSelled() {
        return totalProductsSelled;
    }

    public void setTotalProductsSelled(int totalProductsSelled) {
        this.totalProductsSelled = totalProductsSelled;
    }

    public MarketUser(String name , String password){
        super(name , password);
    }

    public MarketUser(int id, String name, String password , int userType) {
        super(id, name, password , userType);
    }

    public void addToCart(Product p){
        cart.add(p);
    }


    public void ShowCart(){
        System.out.printf("%-20s %-10s", "Name", "Price");
        for(Product p: cart){
            System.out.printf("%-20s %-10d", 
            p.getName(), p.getPrice());
        }
    }

    public void ShowUserProducts() {
        userProducts.getAllProductsBySellerId(AppSession.currentUserId);
    }

    public void ShowPuchaseHistory() {
    
        System.out.printf( "%-25s %-15s %-15s %-20s %-15s " , "Name" , "Price" , "Amount" , "Seller" , "Date");
        for(Purchase purchase: purchaseHistory.getAllPurchasesByUserId(AppSession.currentUserId)){
            
        }
        
    }
}
