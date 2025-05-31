package com.github.daymoon;

import java.sql.*;

public class Favorites {
    
    private int productId;
    private int userId;


    public Favorites(int productId, int userId) {
        this.productId = productId;
        this.userId = userId;
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

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO Favorites(productId , userId ) VALUES( ? , ? )";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.productId);
            pst.setDouble(2, this.userId);

            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
