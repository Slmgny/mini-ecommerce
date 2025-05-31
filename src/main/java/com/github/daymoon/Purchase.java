package com.github.daymoon;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Purchase {
    private int id;
    private LocalDateTime date;
    private String dateString;
    private int amount;
    private int buyerId;
    private int sellerId;
    private int productId;

    //Regular Constructer
    public Purchase(int amount, int buyerId, int sellerId, int productId) {
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.dateString = this.date.format(formatter);
    }

    //For Data Base
    public Purchase(int id, String dateString, int amount, int sellerId, int buyerId, int productId) {
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = LocalDateTime.parse(dateString, formatter);
        this.dateString = dateString;
        this.amount = amount;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public void AddToDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO Purchase (date , amount , sellerId , buyerId , productId) VALUES (? , ? , ? , ? , ?)";
            PreparedStatement pst = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = this.date.format(formatter);

            pst.setString(1, formattedDate);
            pst.setInt(2, this.amount);
            pst.setInt(3, this.sellerId);
            pst.setInt(4, this.buyerId);
            pst.setInt(5, this.productId);

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    
}