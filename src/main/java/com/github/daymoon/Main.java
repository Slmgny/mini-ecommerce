package com.github.daymoon;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.daymoon.DAO.CartDAO;
import com.github.daymoon.DAO.ProductDAO;
import com.github.daymoon.DAO.PurchaseDAO;
import com.github.daymoon.DAO.UserDAO;

public class Main {
    public static void main(String[] args) {
        
        DBConnection conn = new DBConnection();
        conn.connect();

        Scanner sc = new Scanner(System.in);
        UserDAO users = new UserDAO();
        ProductDAO products = new ProductDAO();
        PurchaseDAO purchases = new PurchaseDAO();
        CartDAO carts = new CartDAO();
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Product> productList = new ArrayList<>();
        ArrayList<Purchase> purchaseList = new ArrayList<>();
        ArrayList<Cart> cartList = new ArrayList<>();

        
        for(User u: users.getAllUsers()){

        }



        while (true){
            System.out.println("Welcome Mini E-Commerce Panel");
            System.out.println("Log in or Sign up");
            String userName = sc.nextLine();
            if(userName )

        }
    }
}