package com.github.daymoon;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.daymoon.DAO.CartDAO;
import com.github.daymoon.DAO.ProductDAO;
import com.github.daymoon.DAO.PurchaseDAO;
import com.github.daymoon.DAO.UserDAO;

public class Main {

    Scanner sc = new Scanner(System.in);
    UserDAO users = new UserDAO();
    ProductDAO products = new ProductDAO();
    PurchaseDAO purchases = new PurchaseDAO();
    CartDAO carts = new CartDAO();


    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<Purchase> purchaseList = new ArrayList<>();
    ArrayList<Cart> cartList = new ArrayList<>();

    public static void main(String[] args) {
        Main app = new Main();
        app.init();
        app

        
    }

    private void init(){

        userList.addAll(users.getAllUsers());
        productList.addAll(products.getAllProducts());
        purchaseList.addAll(purchases.getAllPurchases());
        cartList.addAll(carts.getAllCarts());

    }


    

    
    public void run(){
        boolean closeApp = false;
        while(closeApp){
            System.out.println("Welcome to Mini E-Commerce App");
        }
    }


    //Pages
    public void LoginOrSignUpPage(){
        System.out.println("Log in or Sign up");
            String userName = sc.nextLine();
            Boolean newUser = true;

            for(User u: userList){
                if(u.getName().equals(userName)){
                    newUser = false;
                    AppSession.currentUser = u;
                    AppSession.currentUserId = u.id;
                    break;
                }
            }
            if(newUser){
                System.out.println("Create Your Password");
                String password = sc.nextLine();
                if(isPasswordValid(password)){
                    User user = new MarketUser(userName , password , 1);
                    user.AddToDataBase();
                    AppSession.currentUserId = user.id;
                    AppSession.currentUser = user;
                }
                    
            }else{
                System.out.println("Enter Your Password");
                String password = sc.nextLine();
                
                if(isPasswordCorrect(password)){

                }
            }
    }







    public static void printHelp(){
        System.out.println("Type Back to go to previous menu");
        System.out.println("Type Exit to close the App");
        System.out.println("Type Profile to go to Profile menu");
        System.out.println("Type Market to go to Market");
    }





    public static Boolean isPasswordValid(String password){
        if(password.length() < 8){
            System.out.println("Password must be at least 8 characters");
            return false;
        }
        

        if(!password.matches(".*[A-Z].*")){
            System.out.println("Password must contain at least 1 capital letter");
            return false;
        }

        if(!password.matches(".*[a-z].*")){
            System.out.println("Password must contain at least 1 lowercase letter");
            return false;
        }

        if(!password.matches(".*\\d.*")){
            System.out.println("Password must contain at least 1 number");
            return false;
        }

        return true;
    }
    public static Boolean isPasswordCorrect(String password){

        return AppSession.currentUser.password.equals(password);

    }
}