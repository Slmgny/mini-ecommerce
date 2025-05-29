package com.github.daymoon;

import java.util.ArrayList;

public class MarketUser extends User {

    private ArrayList<Product> purchaseHistory = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<Product> marketProducts = new ArrayList<>();

    public MarketUser(String name , String password){
        super(name , password);
    }
}
