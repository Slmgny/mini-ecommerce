package com.github.daymoon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Product {

    private String name;
    private int price;
    private int sellCount;
    private int stock;
    
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        AddToDataBase();
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


    public void AddToDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO Products(name , price) VALUES(?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.name);
            pst.setInt(2, this.price);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void DeleteFromDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT FROM Products WHERE name = ?";
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
