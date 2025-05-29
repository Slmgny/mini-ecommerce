package com.github.daymoon;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    
    public ArrayList<User> getAllUsers(){

        ArrayList<User> users = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";
        String sql = "SELECT id, name, password FROM User";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pst = conn.prepareStatement(sql)) {

                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    int userType = rs.getInt("userType");

                    User user = new MarketUser(id , name , password, userType);
                    users.add(user);
                }

        }catch(Exception e){
            e.printStackTrace();
        }

        return users;
    }

}
