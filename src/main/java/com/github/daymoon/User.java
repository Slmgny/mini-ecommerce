package com.github.daymoon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public abstract class User {
    protected int id;
    protected String name;
    protected String password;
    
    public User(String name , String password){
        this.name = name;
        this.password = password;
        AddToDataBase();
    }
    
    public User(int id, String name , String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void login(){
        System.out.println(name + "Logged in");
    }
    public void logout(){
        System.out.println(name + "Logged out");
    }

    public void AddToDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO Products(name , price) VALUES(?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.name);
            pst.setString(2, this.password);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
