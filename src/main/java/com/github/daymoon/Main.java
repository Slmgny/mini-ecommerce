package com.github.daymoon;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.daymoon.DAO.CartDAO;
import com.github.daymoon.DAO.FavoritesDAO;
import com.github.daymoon.DAO.ProductDAO;
import com.github.daymoon.DAO.PurchaseDAO;
import com.github.daymoon.DAO.UserDAO;

public class Main {

    Scanner sc = new Scanner(System.in);
    UserDAO users = new UserDAO();
    ProductDAO products = new ProductDAO();
    PurchaseDAO purchases = new PurchaseDAO();
    CartDAO carts = new CartDAO();
    FavoritesDAO favorites = new FavoritesDAO();


    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<Purchase> purchaseList = new ArrayList<>();
    ArrayList<Cart> cartList = new ArrayList<>();
    ArrayList<Favorites> favoritesList = new ArrayList<>();

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
            AppSession.currentUserId = -1;
            LoginOrSignUpPage();
            break;
            case "menu":
            if(AppSession.currentUser == null){
                System.out.println("You haven't logged in yet");
                break;
            }
            mainPage();
            break;
            case "back":
            switch (AppSession.previousPage){
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
                walletPage();
                break;
                case 6:
                cartPage();
                break;
                case 7:
                purchasesPage();
                break;
                case 8:
                AddProductPage();
                break;
                default:
                System.out.println("There is no previous page.");
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
    
    private double readDoubleInput(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                return Double.parseDouble(input);
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
        favoritesList.addAll(favorites.getAllFavorites());

    }

    
    public void run(){
        while(true){
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
        String userName = AskName();
        Boolean newUser = true;
        for(User u: userList){
            if(u.getName().equals(userName)){
                newUser = false;
                AppSession.currentUser = u;
                AppSession.currentUserId = u.getId();
                break;
            }
        }
        if(newUser){ // Sign up
            System.out.println("=== SIGN UP ===");
            String password = readInput("Create Your Password: ");
            while(!isPasswordValid(password)){
                password = readInput("Create Your Password: ");
            }
            User user = new MarketUser(userName , password , 1 , 0);
            user.AddToDataBase();
            userList.add(user);
            AppSession.currentUser = user;
            AppSession.currentUserId = user.getId();
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
            System.out.println("User Id: " + AppSession.currentUserId);
            mainPage();
        }
    }

    //Main Page
    public void mainPage(){
        int pagenumber = 2;
        AppSession.currentPage = pagenumber;
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Market");
        System.out.println("2. Profile");
        System.out.println("3. Wallet");
        System.out.println("4. Cart");
        System.out.println("5. Purchases");
        System.out.println("6. Add Your Product");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
        System.out.println("You can type 'help' to see the available commands");
        while(true){
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
                walletPage();
                break;
                case "4":
                AppSession.previousPage = pagenumber;
                cartPage();
                break;
                case "5":
                AppSession.previousPage = pagenumber;
                purchasesPage();
                break;
                case "6":
                AppSession.previousPage = pagenumber;
                AddProductPage();
                break;
                case "7":
                AppSession.previousPage = 1;
                System.out.println("Logging out...");
                AppSession.currentUser=null;
                AppSession.currentUserId = -1;
                LoginOrSignUpPage();
                break;
                case "8":
                System.exit(0);
                break;
                default:
                System.out.println("Please select valid option");
                break;

            }
        }

    }

    //Market Page
    public void marketPage(){
        int pagenumber = 3;
        AppSession.currentPage = pagenumber;
        while(true){
            System.out.println("=== MARKET ===");
            getAllProducts();
            int input = readIntInput("Enter Product Id: ");
            for(Product p: productList){
                if(input == p.getId()){
                    System.out.printf("%-10d %-20s %-10.2f %-10s\n", p.getId() , p.getName(), p.getPrice() , p.getDescription());
                    System.out.printf("\n%-20s %-20s %-20s\n"," 1. Buy " , " 2. Add to Cart " , "3. Add to Favorites" );
                    int input2 = readIntInput("Select an option: ");
                    switch (input2){
                        case 1:
                        int pAmount = readIntInput("Enter Amount: ");
                        if(pAmount <= 0){
                            System.out.println("Amount can't be less than 1");
                            break;
                        }
                        buyProduct(p, pAmount);
                        break;
                        case 2:
                        int amount = readIntInput("Enter the Amount: ");
                        if(amount <= 0){
                            System.out.println("Amount can't be less than 1");
                            break;
                        }
                        boolean inCart = false;
                        for(Cart cart : carts.getCartProductsByUserId(AppSession.currentUserId)){
                            if(cart.getProductId() == p.getId()){
                                cart.updateCartProduct(cart.getAmount() + amount);
                                System.out.println("Successfully Added to Your Cart");
                                inCart = true;
                                break;
                            }
                        }
                        if(!inCart){
                            Cart c = new Cart(AppSession.currentUserId, p.getId(), amount);
                            c.AddToDataBase();
                            cartList.add(c);
                            System.out.println("Successfully Added to Your Cart");
                            break;
                        }
                        break;
                        case 3:
                        Favorites f = new Favorites(p.getId(), AppSession.currentUserId);
                        f.AddToDataBase();
                        favoritesList.add(f);
                        System.out.println("Successfully Added to Your Favorites");
                        break;
                        default:
                        System.out.println("Please select valid option");
                        break;
                    }
                }
            }
        }
        
    }

    //Profile Page
    public void profilePage(){
        int pagenumber = 4;
        AppSession.currentPage = pagenumber;
        User user = AppSession.currentUser;
        while(true){
            System.out.println("=== PROFILE ===");
            System.out.println("1. Edit Your Name");
            System.out.println("2. Change Password");
            System.out.println("3. View Your Products");
            System.out.println("4. View Your Favorites");
            int input = readIntInput("Select an option: ");
            switch (input){
                case 1:
                String password = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password)){
                    System.out.println("Wrong Password!");
                    password = readInput("Enter Your Password: ");
                }
                String newName = AskName();
                user.setName(newName);
                user.updateUser(newName, user.getPassword(), user.getMoney());
                profilePage();
                break;
                case 2:
                String password2 = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password2)){
                    System.out.println("Wrong Password!");
                    password2 = readInput("Enter Your Password: ");
                }
                String newPassword = readInput("Enter Your New Password: ");
                while(!isPasswordValid(newPassword)){
                    newPassword = readInput("Enter Your New Password: ");
                }
                user.setPassword(newPassword);
                user.updateUser(user.getName(), newPassword, user.getMoney());
                profilePage();
                break;
                case 3:

                while(true){
                    getUsersProducts();
                    System.out.printf("\n%-20s %-20s\n" , " 1. Edit Product " , " 2. Go Back ");
                    int i = readIntInput("Select an option: ");
                    switch (i){
                        case 1:
                        int inp = readIntInput("Enter Product ID: ");
                        boolean found = false;
                        for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
                            if(inp == p.getId()){
                                found = true;
                                while(true){
                                    System.out.printf("%-10d %-20s %-10.2f %-10d %-10d %-10s\n", p.getId() , p.getName(), p.getPrice() , p.getStock()
                                    , p.getSellCount(),p.getDescription());
                                    System.out.printf("%-30s %-30s %-30s %-30s\n" , " 1. Change Name " , " 2. Change Price " 
                                    , " 3. Change Stock ", " 4. Change Description " , " 5. Exit");
                                    int option = readIntInput("Select an option: ");
                                    switch (option){
                                        case 1:
                                        String newProductName = AskProductName();
                                        p.setName(newProductName);
                                        p.updateProduct(newProductName, p.getPrice(), p.getSellCount() , p.getStock(), p.getDescription());
                                        profilePage();
                                        break;
                                        case 2:
                                        double newPrice = AskPrice();
                                        p.setPrice(newPrice);
                                        p.updateProduct(p.getName(), newPrice, p.getSellCount() , p.getStock(), p.getDescription());
                                        profilePage();
                                        break;
                                        case 3:
                                        int stock = AskStock();
                                        p.setStock(stock);
                                        p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , stock , p.getDescription());
                                        profilePage();
                                        break;
                                        case 4:
                                        String desc = readInput("Enter new Description");
                                        p.setDescription(desc);
                                        p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , p.getStock() , desc);
                                        profilePage();
                                        break;
                                        case 5:
                                        profilePage();
                                        break;
                                        default:
                                        System.out.println("Please select valid option");
                                        break;
                                    }
                                }  
                            }
                        }
                        if(!found){
                            System.out.println("Product not found");
                            break;
                        }
                        break;
                        case 2:
                        profilePage();
                        break;
                        default:
                        System.out.println("Please select valid option");
                        break;
                    }
                    break;
                }
                case 4:
                favoritesPage();
                break;
                default:
                System.out.println("Please select valid option");
                break;
            }
        }
    }

    //Wallet Page
    public void walletPage(){
        int pagenumber = 5;
        AppSession.currentPage = pagenumber;
        System.out.println("=== Wallet ===");
        System.out.println("Balance: " + AppSession.currentUser.getMoney());
        System.out.println("1. Deposit Money");
        System.out.println("2. Main Menu");
        int input = readIntInput("Select an option: ");
        while(true){
            switch (input){
                case 1:
                double deposit = readIntInput("Deposit amount: ");
                AppSession.currentUser.depositMoney(deposit);
                AppSession.currentUser.updateUser(AppSession.currentUser.getName(), AppSession.currentUser.getPassword(), AppSession.currentUser.getMoney());
                walletPage();
                break;
                case 2:
                mainPage();
                break;
                default:
                System.out.println("Please select valid option");
                break;
            }
        }
    }

    //Cart Page
    public void cartPage(){
        int pagenumber = 6;
        AppSession.currentPage = pagenumber;
        int totalItemsInCart = 0;
        for(Cart c: carts.getCartProductsByUserId(AppSession.currentUserId)){
            totalItemsInCart++;
        }
        while(true){
            System.out.println("=== CART ===");
            if(totalItemsInCart == 0){
                System.out.println("Cart is Empty");
                mainPage();
            }else{
                getUserCart();
                System.out.printf("%-20s %-20s %-20s %-20s\n" , "1. Buy All" , "2. Remove All" , "3. Select Item" , "4. Exit");
                int input = readIntInput("Select an option: ");
                switch (input){
                    case 1:
                    buyCart();
                    break;
                    case 2:
                    for(Cart c: carts.getCartProductsByUserId(AppSession.currentUserId)){
                        c.DeleteFromDataBase();
                        totalItemsInCart++;
                    }
                    if(totalItemsInCart == 0){
                        System.out.println("Cart Is Empty");
                        break;
                    }
                    System.out.println("Products Successfully Deleted! From Cart");
                    break;
                    case 3:
                    int i = readIntInput("Enter Product ID: ");
                    for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
                        Product prod = products.getProductById(c.getProductId());
                        if(prod.getId() == i){
                            System.out.printf("%-20s %-20s %-20s\n" ,"1. Buy" , "2.Remove" , "3.Exit");
                            int inp = readIntInput("Select and option: ");
                            switch (inp){
                                case 1:
                                buyProduct(prod, c.getAmount());
                                cartPage();
                                break;
                                case 2:
                                int amount = readIntInput("Enter amount to delete: ");
                                if(c.getAmount() <= amount){
                                    c.DeleteFromDataBase();
                                    cartList.remove(c);
                                    System.out.println("Product Successfuly Removed From Cart");
                                    cartPage();
                                    break;
                                }else{
                                    c.updateCartProduct(c.getAmount() - amount);
                                    System.out.println(amount + " " + prod.getName() + "left in Cart");
                                    break;
                                }
                                case 3:
                                cartPage();
                                return;
                                default:
                                System.out.println("Please select valid option");
                                break;
                            }
                        }
                    }
                    case 4:
                    mainPage();
                    return;
                    default:
                    System.out.println("Please select valid option");
                    break;
                }
            }
            
        }
    }

    //Purchases Page
    public void purchasesPage(){

        int pagenumber = 7;
        AppSession.currentPage = pagenumber;
        int purchaseCount = 0;
        while(true){
            System.out.println("=== PURCHASE HISTORY ===");
            for(Purchase pur : purchases.getAllPurchasesByUserId(AppSession.currentUserId)){
                purchaseCount++;
            }
            if(purchaseCount > 0){
                getPurchaseHistory();
                System.out.printf("%-20s %-20s\n" , " 1. Request Refund: " , " 2. Main Menu");;
                int input = readIntInput("Select an option: ");
                switch(input){
                    case 1:
                    while(true){
                        int i = readIntInput("Enter Product ID");
                        for(Purchase pur: purchaseList){
                            if(i == pur.getProductId()){
                                System.out.printf("%-20s %-20s\n" , " 1. Confirm " , " 2. Cancel");
                                int option = readIntInput("Select an option: ");
                                switch (option){
                                    case 1:
                                    double price = products.getProductById(pur.getProductId()).getPrice() * pur.getAmount();
                                    AppSession.currentUser.depositMoney(price);
                                    pur.DeleteFromDataBase();
                                    break;
                                    case 2:
                                    break;
                                    default:
                                    System.out.println("Please select valid option");
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    case 2:
                    mainPage();
                    return;
                    default:
                    System.out.println("Please select valid option");
                    break;
            }
            }else{
                System.out.println("Purhcase History is Empty");
                mainPage();
            }
            
        }
    }

    //Add Product Page
    public void AddProductPage(){
        int pagenumber = 8;
        AppSession.currentPage = pagenumber;
        String pName;
        String pDesc;
        double pPrice;
        int pStock;

        System.out.println("=== ADD PRODUCT ===");
        System.out.println("You have to enter your Products Name , Price , Stock and (Optional) Description");
        System.out.println("You can type 'help' to see the available commands");
        while(true){
            pName = AskName();
            pPrice = AskPrice();
            pStock = AskStock();
            pDesc = readInput("(Optional) Description: ");
            
            Product product = new Product(pName, pPrice, pStock, pDesc);
            product.setSellerId(AppSession.currentUserId);
            product.AddToDataBase();
            productList.add(product);
            System.out.println("Product Added!");
            readInput("Type 'menu'to go back to the main menu or press Enter to add another product: ");
        }
    }

    //Favorites Page
    public void favoritesPage(){
        int pagenumber = 9;
        AppSession.currentPage = pagenumber;
        while(true){
            System.out.println("=== FAVORITES ===");
            getFavorites();
            System.out.println("");
            System.out.printf("%-20s %-20s %-20s \n" , "1. Select Item" , "2. Remove All" , "3. Add All to Cart");
            int input = readIntInput("Select an option: ");
            switch(input){
                case 1:
                while(true){
                    int i = readIntInput("Enter Product ID: ");
                    for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                        if(i == f.getProductId()){
                            Product prod = products.getProductById(f.getProductId());
                            System.out.printf("%-10d %-20s %-10.2f\n", f.getProductId(),
                            prod.getName(), prod.getPrice());
                            while(true){
                                System.out.printf("%-20s , %-20s\n" , " 1. Add to Cart " , " 2. Remove from Favorites ");
                                int option = readIntInput("Select an option: ");
                                switch (option){
                                    case 1:
                                    int amount = readIntInput("Enter the amount: ");
                                    Cart c = new Cart(AppSession.currentUserId, f.getProductId(), amount);
                                    c.AddToDataBase();
                                    cartList.add(c);
                                    favoritesPage();
                                    break;
                                    case 2:
                                    f.DeleteFromDataBase();
                                    favoritesList.remove(f);
                                    favoritesPage();
                                    break;
                                    default:
                                    System.out.println("Please select valid option");
                                    break;
                                }
                            }
                            
                        }
                    }
                    break;
                }
                
                case 2:
                for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                    f.DeleteFromDataBase();
                    favoritesList.remove(f);
                }
                favoritesPage();
                break;
                case 3:
                for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                    Cart c = new Cart(AppSession.currentUserId, f.getProductId(), 1);
                    c.AddToDataBase();
                    cartList.add(c);
                }
                cartPage();
                break;
                default:
                System.out.println("Please select valid option");
                break;
            }
        }
    }


    // Help
    public static void printHelp(){
        System.out.println("Type 'back' to go to previous page");
        System.out.println("Type 'exit' to close the App");
        System.out.println("Type 'profile' to go to Profile page");
        System.out.println("Type 'market' to go to Market page");
    }



    // GETTING INFO

    //Name
    public String AskName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = readInput("Name: ");
            if(name.length() < 4){
                System.out.println("User Name must be at least 4 characters");
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println("User Name Must contain at least 1 letter");
                continue;
        }
            isCorrect = true;
            return name;
        }
        return null;

    }

    //Product Name
    public String AskProductName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = readInput("Enter Product Name: ");
            if(name.length() < 4){
                System.out.println("Product Name must be at least 4 characters");
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println("Product Name Must contain at least 1 letter");
                continue;
        }
        isCorrect = true;
        return name;
        }
        return null;

    }

    //Price
    public double AskPrice(){

        Boolean isCorrect = false;
        while(!isCorrect){
            double price = readDoubleInput("Price: ");
            if(price <= 0){
                System.out.println("Price cant be less than or equal to 0");
                continue;
        }
        isCorrect = true;
        return price;
        }
        return -1;
    }

    //Stock
    public int AskStock(){

        Boolean isCorrect = false;
        while(!isCorrect){
            int stock = readIntInput("Stock: ");
            if(stock <= 0){
                System.out.println("stock cant be less than 0");
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

    

    public static Boolean canBuy(double totalPrice){
        if(AppSession.currentUser.getMoney() < totalPrice){
            System.out.println("Failed to Purchase. Insufficient balance");
            return false;
        }
        return true;
    }


    //LISTS

    //Product List
    public void getAllProducts(){
        System.out.printf("%-10s %-20s %-10s %-10s\n", "Id" , "Name", "Price" , "Description\n");
        for(Product p: productList){
            System.out.printf("%-10d %-20s %-10.2f %-10s\n", p.getId() , p.getName(), p.getPrice() , p.getDescription());
        }
    }

    //User's Product List
    public void getUsersProducts(){
        System.out.printf("%-10s %-20s %-10s %-10s %-10s %-10s\n", "Id" , "Name", "Price"  , "Stock" , "Total Purchases" , "Description\n");
        for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
            System.out.printf("%-10d %-20s %-10.2f %-10d %-10d %-10s\n", p.getId() , p.getName(), p.getPrice() , p.getStock()
            , p.getSellCount(),p.getDescription());
        }
    }

    //User's Cart List
    public void getUserCart(){
        double totalPrice = 0;
        System.out.printf("%-10s %-20s %-10s %-10s\n", "Id" , "Name", "Price" , "Amount\n");
        for(Cart c: carts.getCartProductsByUserId(AppSession.currentUserId)){
            Product prod = products.getProductById(c.getProductId());
            System.out.printf("%-10d %-20s %-10.2f %-10d\n", c.getProductId(),
            prod.getName(), prod.getPrice() , c.getAmount());
            totalPrice += prod.getPrice();
            System.out.println("Total: " + totalPrice + " $");
        }
    }

    //Purchase History
    public void getPurchaseHistory(){
        System.out.printf("%-10s %-20s %-10s %-10s %-10s %-15\n", "Id" , "Name", "Price" , "Amount" , "Seller" , "Date\n");
        for(Purchase p: purchaseList){
            Product prod = products.getProductById(p.getProductId());
            System.out.printf("%-10d %-20s %-10.2f %-10d %-10s %-15s\n", p.getProductId(),
            prod.getName(), prod.getPrice() , p.getAmount() ,
            users.getUserById(p.getSellerId()).getName() , p.getDateString());
            
        }
    }

    //Favorites List
    public void getFavorites(){
        System.out.printf("%-10s %-20s %-10s \n", "Id" , "Name", "Price");
        for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
            Product prod = products.getProductById(f.getProductId());
            System.out.printf("%-10d %-20s %-10.2f\n", f.getProductId(),
            prod.getName(), prod.getPrice());
        }
    }





    //BUY

    //Buy Product
    public void buyProduct(Product p , int amount){
        double price = p.getPrice() * amount;
        if(AppSession.currentUser.getMoney() < price){
            System.out.println("Failed to Purchase. Insufficient balance");
        }else if(p.getStock() < amount){
            System.out.println("Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available");
        }else{
            Purchase pur = new Purchase(amount, AppSession.currentUserId, p.getSellerId(), p.getId());
            pur.AddToDataBase();
            purchaseList.add(pur);
            p.SellProduct();
            AppSession.currentUser.Pay(price);
            users.getUserById(p.getSellerId()).depositMoney(price);
            System.out.println("Purchase successful! ");
        }
    }

    //Buy Cart
    public void buyCart(){
        double totalPrice = 0;
        boolean canBuy = true;
        for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
            Product p = products.getProductById(c.getProductId());
            totalPrice += p.getPrice() * c.getAmount();
            if(p.getStock() < c.getAmount()){
                System.out.println("Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available");
                canBuy = false;
            }
        }
        if(totalPrice > AppSession.currentUser.getMoney()){
            System.out.println("Failed to Purchase. Insufficient balance");
            canBuy = false;
        }
        if(canBuy){
            for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
                Product p = products.getProductById(c.getProductId());
                User seller = users.getUserById(p.getSellerId());
                double price = p.getPrice() * c.getAmount();
                Purchase pur = new Purchase(c.getAmount(), AppSession.currentUserId , p.getSellerId() , p.getId() );
                pur.AddToDataBase();
                purchaseList.add(pur);
                AppSession.currentUser.Pay(price);
                seller.depositMoney(price);
                c.DeleteFromDataBase();
                cartList.remove(c);
            }
            System.out.println("Successful! Items in your cart have been purchased.");
            System.out.println("Total Price: " + totalPrice);
            System.out.println("New Balance: " + AppSession.currentUser.getMoney());
            }
        
        
    }
}
