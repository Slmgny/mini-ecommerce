package com.github.daymoon.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.github.daymoon.DBConnection;
import com.github.daymoon.Models.Cart;

public class CartDAO {
    

    public ArrayList<Cart> getAllCarts(){
        ArrayList<Cart> cart = new ArrayList<>();

        String sql = "SELECT userId , productId , amount FROM Cart";

        try(Connection conn = DBConnection.connect();
        PreparedStatement pst = conn.prepareStatement(sql)){

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

    public ArrayList<Cart> getCartProductsByUserId(int userID){
        ArrayList<Cart> cart = new ArrayList<>();

        String sql = "SELECT userId , productId , amount FROM Cart WHERE userId = ?";

        try(Connection conn = DBConnection.connect();
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
