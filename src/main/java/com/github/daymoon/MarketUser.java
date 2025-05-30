package com.github.daymoon;


import com.github.daymoon.DAO.CartDAO;
import com.github.daymoon.DAO.ProductDAO;
import com.github.daymoon.DAO.PurchaseDAO;
import com.github.daymoon.DAO.UserDAO;

public class MarketUser extends User {

    protected int totalProductsSelled;
    private PurchaseDAO purchaseHistory = new PurchaseDAO();
    private ProductDAO products = new ProductDAO();
    private UserDAO userDAO = new UserDAO();
    private CartDAO cartDAO = new CartDAO();
    public int getTotalProductsSelled() {
        return totalProductsSelled;
    }

    public void setTotalProductsSelled(int totalProductsSelled) {
        this.totalProductsSelled = totalProductsSelled;
    }

    public MarketUser(String name , String password , int userType){
        super(name , password , userType);
    }

    public MarketUser(int id, String name, String password , int userType) {
        super(id, name, password , userType);
    }


    public void ShowCart(){
        System.out.printf("%-20s %-10s %-10s\n", "Name", "Price" , "Amount");
        for(Cart c: cartDAO.getCartProductsByUserId(AppSession.currentUserId)){
            System.out.printf("%-20s %-10d %-10d\n" , products.getProductById(c.getProductId()).getName() , products.getProductById(c.getProductId()).getPrice() , c.getAmount());
        }
    }

    public void ShowUserProducts() {
        System.out.printf( "%-25s %-15s %-15s %-15s\n " , "Product Name" , "Price" , "Stock" , "Total Purchases");
        for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
            System.out.printf("%-25s %-15s %-15s %-20s %-15s\n " , p.getName() , p.getPrice() , p.getStock() , p.getSellCount());
            System.out.println();
        }
    }

    public void ShowPurchaseHistory() {
        System.out.printf( "%-25s %-15s %-15s %-20s %-20s\n " , "Product Name" , "Price" , "Amount" , "Seller" , "Date");
        for(Purchase p: purchaseHistory.getAllPurchasesByUserId(AppSession.currentUserId)){
            System.out.printf("%-25s %-15s %-15s %-20s %-15s\n " , products.getProductById(p.getProductId()).getName() , products.getProductById(p.getProductId()).getPrice() , p.getAmount() , userDAO.geUserById(p.getSellerId()).getName() , p.getDate());
            System.out.println();
        }
        
    }
}
