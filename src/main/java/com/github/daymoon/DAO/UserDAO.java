package com.github.daymoon.DAO;

import java.sql.*;
import java.util.ArrayList;

import com.github.daymoon.DBConnection;
import com.github.daymoon.Models.MarketUser;
import com.github.daymoon.Models.User;

public class UserDAO {
    
    public ArrayList<User> getAllUsers(){

        ArrayList<User> users = new ArrayList<>();


        String sql = "SELECT id, name , password , userType , money FROM User";

        try(Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)) {

                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    int userType = rs.getInt("userType");
                    double money = rs.getDouble("money");

                    User user = new MarketUser(id , name , password, userType , money);
                    users.add(user);
                }

        }catch(Exception e){
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userId){
        User user = null;


        String sql = "SELECT id, name , password , userType , money FROM User WHERE id = ?";

        try(Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)){

                pst.setInt(1, userId);
                ResultSet rs = pst.executeQuery();   

                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    int userType = rs.getInt("userType");
                    double money = rs.getDouble("money");


                    user = new MarketUser(id ,name, password , userType , money);
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return user;
    }
    
}
