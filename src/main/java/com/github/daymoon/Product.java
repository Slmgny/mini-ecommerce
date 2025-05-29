package com.github.daymoon;

import java.sql.*;
import java.util.ArrayList;

public class Product {

    private int id;
    private String name;
    private int price;
    private int sellerId;
    private int sellCount;
    private int stock;
    
    public Product(String name, int price , int sellerId , int stock) {
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
        this.stock = stock;
        AddToDataBase();
    }
    
    public Product(int id,String name, int price, int sellCount, int stock , int sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
        this.sellCount = sellCount;
        this.stock = stock;
    }



    //Getter and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    

    public void SellProduct(){
        stock--;
        sellCount++;
    }

    public void AddToDataBase(){
    try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
        String sql = "INSERT INTO Products(name, price, stock, sell_count, selledId) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.name);
        pst.setInt(2, this.price);
        pst.setInt(3, this.stock);
        pst.setInt(4, this.sellCount);
        pst.setInt(5, this.sellerId);
        pst.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}


    public void DeleteFromDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "DELETE FROM Products WHERE name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.name);

            int deleted = pst.executeUpdate();

            if(deleted>0){
                System.out.println("Product Successfully Deleted!");
            }else{
                System.out.println("Failed to Find the Product");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
