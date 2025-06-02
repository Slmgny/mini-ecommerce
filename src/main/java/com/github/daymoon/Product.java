package com.github.daymoon;

import java.sql.*;

public class Product {

    private int id;
    private String name;
    private double price;
    private int sellerId;
    private int sellCount = 0;
    private int stock;
    private String description;
    
    public Product(String name, double price , int stock , String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
    
    public Product(int id,String name, double price, int sellCount, int stock , int sellerId , String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
        this.sellCount = sellCount;
        this.stock = stock;
        this.description = description;
    }



    //Getter and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void SellProduct(){
        stock--;
        sellCount++;
        updateProduct(name, price, sellCount, stock, description);
    }




    //Data Base
    public void AddToDataBase(){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO Product(name, price, stock, sellCount, sellerId , description) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, this.name);
            pst.setDouble(2, this.price);
            pst.setInt(3, this.stock);
            pst.setInt(4, this.sellCount);
            pst.setInt(5, this.sellerId);
            pst.setString(6, this.description);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()){
                this.id = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void DeleteFromDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "DELETE FROM Products WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.id);

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

    
    public boolean updateProduct(String name, double price, int sellCount, int stock , String description) {
        String sql = "UPDATE Product SET name = ? , price = ? , sellCount = ? , stock = ? , description = ? WHERE productId = ?;";

        try (Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, sellCount);
            pst.setInt(4, stock);
            pst.setString(5, description);

            int updated = pst.executeUpdate();
            return updated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    
}
