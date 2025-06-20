package com.github.daymoon.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.github.daymoon.DBConnection;
import com.github.daymoon.Models.Favorites;

public class FavoritesDAO {
    
    public ArrayList<Favorites> getAllFavorites(){
        ArrayList<Favorites> favoritesList = new ArrayList<>();

        String sql = "SELECT userId , productId FROM Favorites";

        try(Connection conn = DBConnection.connect();
        PreparedStatement pst = conn.prepareStatement(sql)){

            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("userId");
                int productId = rs.getInt("productId");


                Favorites f = new Favorites(userId, productId);
                favoritesList.add(f);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return favoritesList;
    }

    public ArrayList<Favorites> getFavoritesByUserId(int userID){
        ArrayList<Favorites> favorites= new ArrayList<>();

        String sql = "SELECT userId , productId FROM Favorites WHERE userId = ?";

        try(Connection conn = DBConnection.connect();
        PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("userId");
                int productId = rs.getInt("productId");

                Favorites c = new Favorites(userId, productId );
                favorites.add(c);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return favorites;
    }

}
