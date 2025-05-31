package com.github.daymoon;

import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;
import javax.swing.SpringLayout;

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
        System.out.println();
        app.run();

        
    }


    private String readInput(String prompt){
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        switch (input.toLowerCase()){
            case "help":
            printHelp();
            return readInput(prompt);
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
            case "main":
            if(AppSession.currentUser == null){
                System.out.println("You haven't logged in yet");
                break;
            }
            mainPage();
            break;
            case "back":
            switch (AppSession.previousPage){
                case 0:
                System.out.println("There is no previous page.");
                break;
                case 1:
                LoginOrSignUpPage();
                break;
                case 2:
                mainPage();
                break;
                case 3:
                marketPage();
                break;
                case 4:
                profilePage();
                break;
                case 5:
                cartPage();
                break;
                case 6:
                purchasesPage();
                break;
            }
            break;
        }
        return input;
    }

    private int readIntInput(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                return Integer.parseInt(input);
            }catch (NumberFormatException e) {
                System.out.println("Please enter a valid number:");
            }
        }
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
    //Login Sign Up Page
    public void LoginOrSignUpPage(){
        int pagenumber = 1;
        AppSession.currentPage = pagenumber;
        System.out.println("=== LOG IN OR SIGN UP ===");
        System.out.println("You can type 'help' to see the available commands");
        String userName = readInput("Name: ");
        Boolean newUser = true;
        for(User u: userList){
            if(u.getName().equals(userName)){
                newUser = false;
                AppSession.currentUser = u;
                break;
            }
        }
        if(newUser){ // Sign up
            System.out.println("=== SIGN UP ===");
            String password = readInput("Create Your Password: ");
            while(!isPasswordValid(password)){
                password = readInput("Create Your Password: ");
            }
            User user = new MarketUser(userName , password , 1);
            user.AddToDataBase();
            userList.add(user);
            AppSession.currentUser = user;
            AppSession.previousPage = pagenumber;
            mainPage();
        }else{ // Login
            System.out.println("=== LOG IN ===");
            String password = readInput("Enter Your Password: ");
            while(!isPasswordCorrect(password)){
                System.out.println("Incorrect Password");
                password = readInput("Enter Your Password: ");
            }
            AppSession.previousPage = pagenumber;
            mainPage();
        }
    }

    //Main Page
    public void mainPage(){
        int pagenumber = 2;
        AppSession.currentPage = pagenumber;
        System.out.println("=== MAİN MENU ===");
        System.out.println("1. Market");
        System.out.println("2. Profile");
        System.out.println("3. Cart");
        System.out.println("4. Purchases");
        System.out.println("5. Add Your Product");
        System.out.println("6. Logout");
        System.out.println("7. Exit");
        System.out.println("You can type 'help' to see the available commands");
        String input = readInput("Select an option: ");
        switch (input){
            case "1":
            AppSession.previousPage = pagenumber;
            marketPage();
            break;
            case "2":
            AppSession.previousPage = pagenumber;
            profilePage();
            break;
            case "3":
            AppSession.previousPage = pagenumber;
            cartPage();
            break;
            case "4":
            AppSession.previousPage = pagenumber;
            purchasesPage();
            break;
            case "5":
            AppSession.previousPage = pagenumber;
            AddProductPage();
            case "6":
            AppSession.previousPage = 1;
            System.out.println("Logging out...");
            AppSession.currentUser=null;
            LoginOrSignUpPage();
            break;
            case "7":
            System.exit(0);
            break;

        }

    }

    //Market Page
    public void marketPage(){
        int pagenumber = 3;
        AppSession.currentPage = pagenumber;
        System.out.println("=== MARKET ===");
        getAllProducts();
    }

    //Profile Page
    public void profilePage(){
        int pagenumber = 4;
        AppSession.currentPage = pagenumber;
        System.out.println("=== PROFİLE ===");

    }

    //Cart Page
    public void cartPage(){
        int pagenumber = 5;
        AppSession.currentPage = pagenumber;
        System.out.println("=== CART ===");
    }

    //Purchases Page
    public void purchasesPage(){
        int pagenumber = 6;
        AppSession.currentPage = pagenumber;
        System.out.println("=== PURCHASE HISTORY ===");
    }

    //Add Product Page
    public void AddProductPage(){
        int pagenumber = 7;
        AppSession.currentPage = pagenumber;
        String pName;
        String pDesc;
        int pPrice;
        int pStock;

        System.out.println("=== ADD PRODUCT ===");
        System.out.println("You have to enter your Products Name , Price , Stock and (Optional) Description");
        pName = AskName();
        pPrice = AskPrice();
        pStock = AskScock();
        pDesc = readInput("(Optional) Description: ");
        
        Product product = new Product(pName, pPrice, pStock, pDesc);
        product.setSellerId(AppSession.currentUserId);
        product.AddToDataBase();
        productList.add(product);
        
    }



    // Help
    public static void printHelp(){
        System.out.println("Type Back to go to previous page");
        System.out.println("Type Exit to close the App");
        System.out.println("Type Profile to go to Profile page");
        System.out.println("Type Market to go to Market page");
    }



    // Getting info

    //Name
    public String AskName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = readInput("Name: ");
            if(name.length() < 4){
                System.out.println("Your Name must be at least 4 characters");
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println("Your Name Must contain at least 1 letter");
                continue;
        }
        isCorrect = true;
        return name;
        }
        return "";

    }

    //Price
    public int AskPrice(){

        Boolean isCorrect = false;
        while(!isCorrect){
            int price = readIntInput("Price: ");
            if(price <= 0){
                System.out.println("Price cant be lower that 0");
                continue;
        }
        isCorrect = true;
        return price;
        }
        return -1;
    }

    //Stock
    public int AskScock(){

        Boolean isCorrect = false;
        while(!isCorrect){
            int stock = readIntInput("Stock: ");
            if(stock <= 0){
                System.out.println("stock cant be lower that 0");
                continue;
        }
        isCorrect = true;
        return stock;
        }
        return -1;
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
        return AppSession.currentUser.getPassword().equals(password);
    }





    //Product List
    public void getAllProducts(){
        System.out.printf("%-10d %-20s %-10s %-10s\n", "Id" , "Name", "Price" , "Description");
        for(Product p: productList){
            System.out.printf("%-10d %-20s %-10s %-10s\n", p.getId() , p.getName(), p.getPrice() , p.getDescription());
        }
    }
}