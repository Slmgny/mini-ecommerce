package com.github.daymoon;


public abstract class User {
    protected String name;
    protected String password;
    
    public User(String name , String password){
        this.name = name;
        this.password = password;
    }

    public void login(){
        System.out.println(name + "Logged in");
    }
    public void logout(){
        System.out.println(name + "Logged out");
    }
}
