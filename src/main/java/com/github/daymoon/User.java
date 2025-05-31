package com.github.daymoon;

import java.sql.*;

public abstract class User {
    protected int id;
    protected String name;
    protected String password;
    protected int userType;
    protected double money;
    
    public User(String name , String password , int userType , double  money){
        this.name = name;
        this.password = password;
        this.userType = userType;
        this.money = money;
    }

    public User(int id, String name , String password , int userType , double money){
        this.id = id;
        this.name = name;
        this.password = password;
        this.userType = userType;
        this.money = money;
    }

   
    //Getter and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }


    // Login & Logout
    public void login(){
        System.out.println(name + "Logged in");
    }
    public void logout(){
        System.out.println(name + "Logged out");
    }


    // Wallet
    public void depositMoney(double depositMoney){
        this.money =+ depositMoney;
        System.out.println("New balance: " + this.money);
    }
    public void Pay(double pay){
        this.money =- pay;
        System.out.println("New balance: " + this.money);
    }


    public void AddToDataBase(){

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db")){
            String sql = "INSERT INTO User(name , password , userType , money) VALUES( ? , ? , ? , ?)";
            PreparedStatement pst = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, this.name);
            pst.setString(2, this.password);
            pst.setInt(3, this.userType);
            pst.setDouble(4, this.money);


            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()){
                this.id = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    
}
