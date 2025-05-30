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
        app.run();

        
    }


    private String readInput(){
        String input = sc.nextLine().trim();
        switch (input.toLowerCase()){
            case "help":
            printHelp();
            return readInput();
            case "exit":
            System.exit(0);
            break;
            case "logout":
            if(AppSession.currentUser == null){
                System.out.println("You haven't logged in yet");
                break;
            }
            System.out.println("Logging out...");
            AppSession.currentUser=null;
            LoginOrSignUpPage();
            break;
        }
        return input;
    }


    private void init(){

        userList.addAll(users.getAllUsers());
        productList.addAll(products.getAllProducts());
        purchaseList.addAll(purchases.getAllPurchases());
        cartList.addAll(carts.getAllCarts());

    }

    
    public void run(){
        boolean closeApp = false;
        while(!closeApp){
            System.out.println("Welcome to Mini E-Commerce App");
            LoginOrSignUpPage();
        }
    }


    //Pages
    public void LoginOrSignUpPage(){
        System.out.println("Log in or Sign up");
            String userName = readInput();
            Boolean newUser = true;

            for(User u: userList){
                if(u.getName().equals(userName)){
                    newUser = false;
                    AppSession.currentUser = u;
                    break;
                }
            }
            if(newUser){
                System.out.println("Create Your Password");
                String password = readInput();
                if(isPasswordValid(password)){
                    User user = new MarketUser(userName , password , 1);
                    user.AddToDataBase();
                    AppSession.currentUserId = user.id;
                    AppSession.currentUser = user;
                }
                    
            }else{
                System.out.println("Enter Your Password");
                String password = readInput();
                
                if(isPasswordCorrect(password)){

                }
            }
    }







    public static void printHelp(){
        System.out.println("=== HELP MENU ===");
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