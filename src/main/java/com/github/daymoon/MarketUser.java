package com.github.daymoon;

import java.util.ArrayList;

public class MarketUser extends User {

    protected int totalProductsSelled;
    private ArrayList<Product> cart = new ArrayList<>();
    private PurchaseDAO purchaseHistory = new PurchaseDAO();
    private ProductDAO products = new ProductDAO();
    private UserDAO userDAO = new UserDAO();

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
        System.out.printf( "%-25s %-15s %-15s %-15s " , "Product Name" , "Price" , "Stock" , "Total Purchases");
        for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
            System.out.printf("%-25s %-15s %-15s %-20s %-15s " , p.getName() , p.getPrice() , p.getStock() , p.getSellCount());
            System.out.println();
        }
    }

    public void ShowPuchaseHistory() {
        System.out.printf( "%-25s %-15s %-15s %-20s %-20s " , "Product Name" , "Price" , "Amount" , "Seller" , "Date");
        for(Purchase p: purchaseHistory.getAllPurchasesByUserId(AppSession.currentUserId)){
            System.out.printf("%-25s %-15s %-15s %-20s %-15s " , products.getProductById(p.getProductId()).getName() , products.getProductById(p.getProductId()).getPrice() , p.getAmount() , userDAO.geUserById(p.getSellerId()).getName() , p.getDate());
            System.out.println();
        }
        
    }
}
