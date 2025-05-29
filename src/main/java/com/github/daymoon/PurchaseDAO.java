package com.github.daymoon;

import java.sql.*;
import java.util.ArrayList;

public class PurchaseDAO {
    //All
    public ArrayList<Purchase> getAllPurchases() {
        ArrayList<Purchase> purchases = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";
        String sql = "SELECT id, date, amount, sellerId, buyerId, productId  FROM Purchase";

        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                int amount = rs.getInt("amount");
                int sellerId = rs.getInt("sellerId");
                int buyerId = rs.getInt("buyerId");
                int productId = rs.getInt("productId");

                Purchase Purchase = new Purchase(id, date, amount, sellerId, buyerId , productId);
                purchases.add(Purchase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }


    //By User Id
    public ArrayList<Purchase> getAllPurchasesByUserId(int UserId) {
        ArrayList<Purchase> purchases = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";
        String sql = "SELECT id, date, amount, sellerId, buyerId, productId FROM Purchase WHERE buyerId = ?";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, UserId);  
            ResultSet rs = pst.executeQuery();  

            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                int amount = rs.getInt("amount");
                int sellerId = rs.getInt("sellerId");
                int buyerId = rs.getInt("buyerId");
                int productId = rs.getInt("productId");

                Purchase purchase = new Purchase(id, date, amount, sellerId, buyerId , productId);
                purchases.add(purchase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }

}
