package com.github.daymoon.DAO;

import java.sql.*;
import java.util.ArrayList;

import com.github.daymoon.DBConnection;
import com.github.daymoon.MarketUser;
import com.github.daymoon.User;

public class UserDAO {
    
    public ArrayList<User> getAllUsers(){

        ArrayList<User> users = new ArrayList<>();


        String sql = "SELECT id, name, password , userType FROM User";

        try(Connection conn = DBConnection.connect();
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

    public User getUserById(int userId){
        User user = null;


        String sql = "SELECT id, name, password , userType FROM User WHERE id = ?";

        try(Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)){

                pst.setInt(1, userId);
                ResultSet rs = pst.executeQuery();   

                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    int userType = rs.getInt("userType");

                    user = new MarketUser(id ,name, password , userType);
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return user;
    }
    
}
