package com.github.daymoon.Models;

import java.sql.*;

import com.github.daymoon.DBConnection;

public class Favorites {
    
    private int productId;
    private int userId;


    public Favorites(int userId , int productId) {
        this.userId = userId;
        this.productId = productId;
        
    }


    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }



    public void AddToDataBase(){

        try(Connection conn = DBConnection.connect()){
            String sql = "INSERT INTO Favorites( userId , productId  ) VALUES( ? , ? )";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.userId);
            pst.setInt(2, this.productId);
            

            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void DeleteFromDataBase(){

        try(Connection conn = DBConnection.connect()){
            String sql = "DELETE FROM Favorites WHERE productId = ? AND userId = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.productId);
            pst.setInt(2, this.userId);
            int deleted = pst.executeUpdate();
            if(deleted>0){
            }else{
                System.out.println("Failed to Find the Favorite Product");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
