package com.github.daymoon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CartDAO {
    

    public ArrayList<Cart> getCartsByUserId(int userID){
        ArrayList<Cart> cart = new ArrayList<>();

        String sql = "SELECT userId , productId , amount FROM Cart WHERE userId = ?";
        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";

        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("userId");
                int productId = rs.getInt("productId");
                int amount = rs.getInt("amount");

                Cart c = new Cart(userId, productId , amount);
                cart.add(c);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return cart;
    }
}
