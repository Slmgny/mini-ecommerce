package com.github.daymoon.Models;

import java.sql.*;

import com.github.daymoon.DBConnection;

public class Cart {
    private int amount;
    private int buyerId;
    private int productId;


    public Cart(int buyerId, int productId, int amount) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
        updateCartProduct(amount);
    }


    public int getBuyerId() {
        return buyerId;
    }


    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }


    public int getProductId() {
        return productId;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void AddToDataBase(){

        try(Connection conn = DBConnection.connect()){
            String sql = "INSERT INTO Cart (userId , productId , amount) VALUES (? , ? , ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, this.buyerId);
            pst.setInt(2, this.productId);
            pst.setInt(3, this.amount);

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void DeleteFromDataBase(){

        try(Connection conn = DBConnection.connect()){
            String sql = "DELETE FROM Cart WHERE productId = ? AND userId = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.productId);
            pst.setInt(2, this.buyerId);

            int deleted = pst.executeUpdate();

            if(deleted>0){
            }else{
                System.out.println("Failed to Find the Cart Product");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean updateCartProduct(int amount) {
        String sql = "UPDATE Cart SET amount = ? WHERE productId = ?;";

        try (Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, amount);
            pst.setInt(2, productId);

            int updated = pst.executeUpdate();
            return updated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
