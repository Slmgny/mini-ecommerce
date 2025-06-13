package com.github.daymoon;

import java.util.ArrayList;
import java.util.Iterator;
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
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36;1m";
    public static final String YELLOW = "\u001B[33;1m";
    public static final String GREEN = "\u001B[32;1m";
    public static final String RED = "\u001B[31;1m";
    public static final String MAGENTA = "\u001B[35;1m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLUE = "\u001B[34;1m";

    private String readInput(String prompt){
        System.out.print(BLUE + prompt + RESET);
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
                System.out.println(RED + "You haven't logged in yet" + RESET);
                break;
            }
            System.out.println(RED + "Logging out..." + RESET);
            AppSession.currentUser=null;
            AppSession.currentUserId = -1;
            LoginOrSignUpPage();
            break;
        case "back":
           if (AppSession.currentUser == null) {
                System.out.println(RED + "You haven't logged in yet" + RESET);
                return readInput(prompt);
            } else {
                if (!navigateToPage(AppSession.previousPage)) {
                    System.out.println(RED + "You can't go to previous page." + RESET);
                    return readInput(prompt);
                }
            }
            return "";
        case "cancel":
            if (!navigateToPage(AppSession.currentPage)) {
                System.out.println(RED + "There is no current page." + RESET);
            }
            return "";
        case "menu":
            return goToIfLoggedIn(2); // mainPage

        case "market":
            return goToIfLoggedIn(3); // marketPage

        case "profile":
            return goToIfLoggedIn(4); // profilePage

        case "wallet":
            return goToIfLoggedIn(5); // walletPage

        case "cart":
            return goToIfLoggedIn(6); // cartPage

        case "fav":
        case "favorites":
            return goToIfLoggedIn(9); // favoritesPage

        case "purch":
        case "purchases":
            return goToIfLoggedIn(7); // purchasesPage

        case "add":
            return goToIfLoggedIn(8); // AddProductPage
        case "user":
            System.out.println(AppSession.currentUser);
            readInput(prompt);
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
                System.out.println( RED + "Please enter a valid number:" + RESET);
            }
        }
    }
    
    private double readDoubleInput(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                return Double.parseDouble(input);
            }catch (NumberFormatException e) {
                    System.out.println( RED + "Please enter a valid number:" + RESET);

            }
        }
    }

    private boolean navigateToPage(int pageNumber) {
        AppSession.previousPage = AppSession.currentPage;
        AppSession.currentPage = pageNumber;

        switch (pageNumber) {
            case 1: LoginOrSignUpPage(); break;
            case 2: mainPage(); break;
            case 3: marketPage(); break;
            case 4: profilePage(); break;
            case 5: walletPage(); break;
            case 6: cartPage(); break;
            case 7: purchasesPage(); break;
            case 8: AddProductPage(); break;
            case 9: favoritesPage(); break;
            default: return false;
        }
            return true;
    }
    private String goToIfLoggedIn(int pageNumber) {
        if (AppSession.currentUser == null) {
            System.out.println(RED + "You haven't logged in yet" + RESET);
            return readInput(">> ");
        }
        navigateToPage(pageNumber);
        return "";
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
            System.out.println(BLUE + "Welcome to Mini E-Commerce App" + RESET);
            LoginOrSignUpPage();
        }
    }


    //Pages
    //Login Sign Up Page
    public void LoginOrSignUpPage(){
        int pagenumber = 1;
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "=== LOG IN OR SIGN UP ===" + RESET);
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
        String userName = AskName();
        boolean newUser = true;
        User currentUser = null;
        for(User u: userList){
            if(u.getName().equals(userName)){
                newUser = false;
                currentUser = u;
                break;
            }
        }
        if(newUser){ // Sign up
            System.out.println(CYAN + "=== SIGN UP ===" + RESET);
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
            System.out.println(CYAN + "=== LOG IN ===" + RESET);
            String password = readInput("Enter Your Password: ");
            while(!isPasswordCorrect(password , currentUser)){
                System.out.println(RED + "Incorrect Password" + RESET);
                password = readInput("Enter Your Password: ");
            }
            AppSession.currentUser = currentUser;
            AppSession.currentUserId = currentUser.getId();
            AppSession.previousPage = pagenumber;
            mainPage();
        }
    }


    //Main Page
    public void mainPage(){
        int pagenumber = 2;
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "=== MAIN MENU ===" + RESET);
        System.out.println(YELLOW + "1." + RESET + " Market");
        System.out.println(YELLOW + "2." + RESET + " Profile");
        System.out.println(YELLOW + "3." + RESET + " Wallet");
        System.out.println(YELLOW + "4." + RESET + " Cart");
        System.out.println(YELLOW + "5." + RESET + " Purchases");
        System.out.println(YELLOW + "6." + RESET + " Add Your Product");
        System.out.println(RED + "7." + RESET + " Logout");
        System.out.println(RED + "8." + RESET + " Exit");
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);

        while(true){
        String input = readInput(CYAN + "Select an option: " + RESET);
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
                System.out.println(RED + "Logging out..." + RESET);
                AppSession.currentUser=null;
                AppSession.currentUserId = -1;
                LoginOrSignUpPage();
                break;
                case "8":
                System.exit(0);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                break;

            }
        }
    }

    //Market Page
    public void marketPage(){
        int pagenumber = 3;
        AppSession.currentPage = pagenumber;
        boolean valid = false;
        while(true){
            System.out.println(CYAN + "=== MARKET ===" + RESET);
            getAllProducts();
            int input = readIntInput("Enter Product Id: ");
            for(Product p: productList){
                if(input == p.getId()){
                    valid = true;
                    System.out.printf(  YELLOW + "%-10d"+ CYAN +"%-20s" + GREEN +"%-10.2f"+ WHITE +"%-10s\n" + RESET,
                    p.getId() , p.getName(), p.getPrice() ,p.getDescription());
                    System.out.printf("\n" + YELLOW +"%-20s %-20s %-20s\n" + RESET," 1. Buy " , " 2. Add to Cart " , "3. Add to Favorites" );
                    int input2 = readIntInput("Select an option: ");
                    switch (input2){
                        case 1:
                        int pAmount = readIntInput("Enter Amount: ");
                        if(pAmount <= 0){
                            System.out.println(RED + "Amount can't be less than 1" + RESET);
                            break;
                        }
                        buyProduct(p, pAmount);
                        break;
                        case 2:
                        int amount = readIntInput("Enter the Amount: ");
                        if(amount <= 0){
                            System.out.println(RED +"Amount can't be less than 1" + RESET);
                            break;
                        }
                        boolean inCart = false;
                        for(Cart cart : carts.getCartProductsByUserId(AppSession.currentUserId)){
                            if(cart.getProductId() == p.getId()){
                                cart.updateCartProduct(cart.getAmount() + amount);
                                System.out.println(GREEN +"Successfully Added to Your Cart" + RESET);
                                inCart = true;
                                break;
                            }
                        }
                        if(!inCart){
                            Cart c = new Cart(AppSession.currentUserId, p.getId(), amount);
                            c.AddToDataBase();
                            cartList.add(c);
                            System.out.println(GREEN + "Successfully Added to Your Cart"+ RESET);
                            break;
                        }
                        break;
                        case 3:
                        boolean inFav = false;
                        for(Favorites fav: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                            if(fav.getProductId() == p.getId()){
                                System.out.println(RED + "Product is already in Favorites" + RESET);
                                inFav = true;
                                break;
                            }
                        }
                        if(!inFav){
                            Favorites f = new Favorites(AppSession.currentUserId , p.getId());
                            f.AddToDataBase();
                            favoritesList.add(f);
                            System.out.println(GREEN + "Successfully Added to Your Favorites" + RESET);
                            break;
                        }
                        break;
                        default:
                        System.out.println(RED + "Please select valid option" + RESET);
                        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                        break;
                    }
                }
            }
            if(!valid){
                System.out.println(RED + "Product not found. Try again." + RESET);
            }
        }
    }

    //Profile Page
    public void profilePage(){
        int pagenumber = 4;
        AppSession.currentPage = pagenumber;
        User user = AppSession.currentUser;
        while(true){
            System.out.println(CYAN + "=== PROFILE ===" + RESET);
            System.out.println(YELLOW + "1." + RESET + " Edit Your Name");
            System.out.println(YELLOW + "2." + RESET + " Change Password");
            System.out.println(YELLOW + "3." + RESET + " View Your Products");
            System.out.println(YELLOW + "4." + RESET + " View Your Favorites");
            int input = readIntInput("Select an option: ");
            switch (input){
                case 1:
                String password = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password = readInput("Enter Your Password: ");
                    
                }
                String newName = AskName();
                System.out.println(GREEN + "Name updated successfully!" + RESET);
                user.setName(newName);
                user.updateUser(newName, user.getPassword(), user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 2:
                String password2 = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password2 , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password2 = readInput("Enter Your Password: ");
                }
                String newPassword = readInput("Enter Your New Password: ");
                while(!isPasswordValid(newPassword)){
                    newPassword = readInput("Enter Your New Password: ");
                }
                user.setPassword(newPassword);
                System.out.println(GREEN + "Password updated successfully!" + RESET);
                user.updateUser(user.getName(), newPassword, user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 3:
                userProductsPage();
                break;
                case 4:
                AppSession.previousPage = pagenumber;
                favoritesPage();
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
        }
    }

    //Wallet Page
    public void walletPage(){
        int pagenumber = 5;
        AppSession.currentPage = pagenumber;
        while(true){
            System.out.println(CYAN + "=== Wallet ===" + RESET);
            System.out.println(GREEN + "Balance: " + AppSession.currentUser.getMoney() + RESET);
            System.out.println(YELLOW + "1." + RESET +"Deposit Money");
            System.out.println(YELLOW + "2."+ RESET +"Main Menu");
            int input = readIntInput("Select an option: ");
            switch (input){
                case 1:
                double deposit = readIntInput("Deposit amount: ");
                AppSession.currentUser.depositMoney(deposit);
                AppSession.currentUser.updateUser(AppSession.currentUser.getName(), AppSession.currentUser.getPassword(), AppSession.currentUser.getMoney());
                continue;
                case 2:
                AppSession.previousPage = pagenumber;
                mainPage();
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
        }
    }

    //Cart Page
    public void cartPage(){
        int pagenumber = 6;
        AppSession.currentPage = pagenumber;
        while(true){
            int totalItemsInCart = carts.getCartProductsByUserId(AppSession.currentUserId).size();
            System.out.println(CYAN + "=== CART ===" + RESET);
            if(totalItemsInCart == 0){
                System.out.println(RED + "Cart is Empty" + RESET);
                AppSession.previousPage = pagenumber;
                mainPage();
            }else{
                getUserCart();
                System.out.printf(YELLOW + "%-20s %-20s %-20s %-20s\n" + RESET , "1. Buy All" , "2. Remove All" , "3. Select Item" , "4. Exit");
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
                        System.out.println(RED + "Cart Is Empty" + RESET);
                        break;
                    }
                    System.out.println(GREEN + "Products Successfully Deleted! From Cart" + RESET);
                    break;
                    case 3:
                    int i = readIntInput("Enter Product ID: ");
                    for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
                        Product prod = products.getProductById(c.getProductId());
                        if(prod.getId() == i){
                            System.out.printf(YELLOW + "%-20s %-20s %-20s\n" + RESET ,"1. Buy" , "2.Remove" , "3.Exit");
                            int inp = readIntInput("Select and option: ");
                            switch (inp){
                                case 1:
                                buyProduct(prod, c.getAmount());
                                continue;
                                case 2:
                                int amount = readIntInput("Enter amount to delete: ");
                                if(c.getAmount() <= amount){
                                    c.DeleteFromDataBase();
                                    cartList.remove(c);
                                    System.out.println(GREEN + "Product Successfuly Removed From Cart" + RESET);
                                    continue;
                                }else{
                                    c.updateCartProduct(c.getAmount() - amount);
                                    System.out.println(GREEN + amount + " unit(s) of " + prod.getName() + " left in your cart." + RESET);

                                    break;
                                }
                                case 3:
                                continue;
                                default:
                                System.out.println(RED + "Please select valid option" + RESET);
                                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                                break;
                            }
                        }
                    }
                    break;
                    case 4:
                    AppSession.previousPage = pagenumber;
                    mainPage();
                    return;
                    default:
                    System.out.println(RED + "Please select valid option" + RESET);
                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                    break;
                }
            }
            
        }
    }

    //Purchases Page
    public void purchasesPage(){
        int pagenumber = 7;
        AppSession.currentPage = pagenumber;
        boolean found = false;
        while(true){
            System.out.println(CYAN + "=== PURCHASE HISTORY ===" + RESET);
            int purchaseCount = purchases.getAllPurchasesByUserId(AppSession.currentUserId).size();
            if(purchaseCount > 0){
                getPurchaseHistory();
                System.out.printf(YELLOW + "%-20s %-20s\n" + RESET , " 1. Request Refund: " , " 2. Main Menu");;
                int input = readIntInput("Select an option: ");
                switch(input){
                    case 1:
                    while(true){
                        int i = readIntInput("Enter Purchase ID: ");
                        Iterator <Purchase> iterator = purchaseList.iterator(); 
                        while (iterator.hasNext()) {
                            Purchase pur = iterator.next();
                            if (pur.getId() == i) {
                                System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                                int option = readIntInput("Select an option: ");
                                switch (option) {
                                    case 1:
                                        double price = products.getProductById(pur.getProductId()).getPrice() * pur.getAmount();
                                        AppSession.currentUser.depositMoney(price);
                                        iterator.remove();
                                        pur.DeleteFromDataBase();
                                        System.out.println(GREEN + "Refund completed." + RESET);
                                        break;
                                    case 2:
                                        System.out.println(YELLOW + "Cancelled." + RESET);
                                        break;
                                    default:
                                        System.out.println(RED + "Please select a valid option." + RESET);
                                        break;
                                }
                                found = true;
                                break;
                            }
                        }
                    if (!found) {
                    System.out.println(RED + "Purchase not found." + RESET);
                    }
                    break;
                    }
                    case 2:
                    AppSession.previousPage = pagenumber;
                    mainPage();
                    return;
                    default:
                    System.out.println(RED + "Please select valid option" + RESET);
                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                    break;
            }
            }else{
                System.out.println(RED +"Purhcase History is Empty"  + RESET);
                AppSession.previousPage = pagenumber;
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

        System.out.println(CYAN + "=== ADD PRODUCT ===" + RESET);
        System.out.println(YELLOW + "You have to enter your Products Name , Price , Stock and (Optional) Description" + RESET);
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);

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
            readInput(YELLOW + "Type " + MAGENTA  + "'menu' " + YELLOW +"to go back to the main menu or press Enter to add another product: "+ RESET);
        }
    }

    //Favorites Page
    public void favoritesPage(){
        int pagenumber = 9;
        AppSession.currentPage = pagenumber;
        int favotiresCount = favorites.getFavoritesByUserId(AppSession.currentUserId).size();
        if(favotiresCount == 0){
            System.out.println(RED + "Favorites empty" + RESET);
            navigateToPage(AppSession.previousPage);
            return;
        }
        while(true){
            System.out.println(CYAN + "=== FAVORITES ===" + RESET);
            getFavorites();
            System.out.println("");
            System.out.printf(YELLOW +"%-20s %-20s %-20s \n" + RESET , "1. Select Item" , "2. Remove All" , "3. Add All to Cart");
            int input = readIntInput("Select an option: ");
            switch(input){
                case 1:
                while(true){
                    int i = readIntInput("Enter Product ID: ");
                    for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                        if(i == f.getProductId()){
                            Product prod = products.getProductById(f.getProductId());
                            System.out.printf(YELLOW + "%-10d" + MAGENTA + "%-20s" + GREEN + "%-10.2f\n" + RESET, f.getProductId(),
                            prod.getName(), prod.getPrice());;
                            while(true){
                                System.out.printf(YELLOW + "%-20s , %-20s\n" + RESET , " 1. Add to Cart " , " 2. Remove from Favorites ");
                                int option = readIntInput("Select an option: ");
                                switch (option){
                                    case 1:
                                    int amount = readIntInput("Enter the amount: ");
                                    Cart c = new Cart(AppSession.currentUserId, f.getProductId(), amount);
                                    c.AddToDataBase();
                                    cartList.add(c);
                                    System.out.println(GREEN + "Added to cart." + RESET);
                                    favoritesPage();
                                    break;
                                    case 2:
                                    f.DeleteFromDataBase();
                                    favoritesList.remove(f);
                                    System.out.println(GREEN + "Removed from favorites." + RESET);
                                    favoritesPage();
                                    break;
                                    default:
                                    System.out.println(RED + "Please select valid option" + RESET);
                                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
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
                AppSession.previousPage = pagenumber;
                favoritesPage();
                break;
                case 3:
                for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
                    boolean found = false;
                    Product prod = products.getProductById(f.getProductId());
                    int amount = readIntInput("Enter amount for " + prod.getName() + ": ");
                    Iterator<Cart> iter = cartList.iterator();
                    while(iter.hasNext()){
                        Cart cart = iter.next();
                        if(cart.getProductId() == prod.getId()){
                            cart.setAmount(cart.getAmount() + amount);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        Cart c = new Cart(AppSession.currentUserId, f.getProductId(), amount);
                        c.AddToDataBase();
                        cartList.add(c);
                    }
                }
                AppSession.previousPage = pagenumber;
                cartPage();
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                break;
            }
        }
    }

    //User Products Page
    public void userProductsPage(){
        int pagenumber = 10;
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "===YOUR PRODUCTS===" + RESET);
        while(true){
            getUsersProducts();
            System.out.printf(YELLOW + "\n%-20s %-20s\n" + RESET , " 1. Edit Product " , " 2. Go Back ");
            int i = readIntInput("Select an option: ");
            switch (i){
                case 1:
                int inp = readIntInput("Enter Product ID: ");
                boolean found = false;
                for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
                    if(inp == p.getId()){
                        found = true;
                        while(true){
                            System.out.printf(YELLOW + "%-10d"+ CYAN +"%-20s"+ GREEN +"%-10.2f"+ MAGENTA +"%-10d"+ BLUE +"%-17d"+ WHITE +"%-10s\n"+ RESET,
                            p.getId() , p.getName(), p.getPrice() , p.getStock(),
                            p.getSellCount(),p.getDescription());
                            System.out.printf(YELLOW  + "%-20s %-20s %-20s %-20s %-20s %-20s\n" + RESET , " 1. Change Name " , " 2. Change Price " 
                            , " 3. Change Stock ", " 4. Change Description " , " 5. Delete Product" , " 6. Exit");
                            int option = readIntInput("Select an option: ");
                            switch (option){
                                case 1:
                                String newProductName = AskProductName();
                                p.setName(newProductName);
                                p.updateProduct(newProductName, p.getPrice(), p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 2:
                                double newPrice = AskPrice();
                                p.setPrice(newPrice);
                                p.updateProduct(p.getName(), newPrice, p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 3:
                                int stock = AskStock();
                                p.setStock(stock);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , stock , p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 4:
                                String desc = readInput("Enter new Description");
                                p.setDescription(desc);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , p.getStock() , desc);
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 5:
                                p.DeleteFromDataBase();
                                productList.remove(p);
                                System.out.println(GREEN + "Successfuly Deleted the Product" + RESET);
                                navigateToPage(pagenumber);
                                case 6:
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                default:
                                System.out.println(RED + "Please select valid option" + RESET);
                                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                                break;
                            }
                        }  
                    }
                }
                if(!found){
                    System.out.println(RED + "Product not found" + RESET);
                    break;
                }
                break;
                case 2:
                AppSession.previousPage = pagenumber;
                profilePage();
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
            break;
        }
    }


    // Help
    public static void printHelp(){
        System.out.println("Type " + MAGENTA + "'back'" + RESET + " to go to the " + YELLOW + "previous page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'exit'" + RESET + " to " + YELLOW + "close the app" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'logout'" + RESET + " to " + YELLOW + "logut" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'cancel'" + RESET + " to " + YELLOW + "restart this page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'profile'" + RESET + " to go to " + YELLOW + "your profile" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'market'" + RESET + " to go to the " + YELLOW + "market page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'menu'" + RESET + " to go to the " + YELLOW + "main menu" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'wallet'" + RESET + " to go to " + YELLOW + "your wallet" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'cart'" + RESET + " to view " + YELLOW + "your cart" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'fav'" + RESET + " or " + MAGENTA + "'favorites'" + RESET + " to view " + YELLOW + "your favorites" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'purch'" + RESET + " or " + MAGENTA + "'purchases'" + RESET + " to view " + YELLOW + "your purchases" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'add'" + RESET + " to go to the " + YELLOW + "add product page" + RESET + ".");

    }



    // GETTING INFO
    //Name
    public String AskName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = readInput("Name: ");
            if(name.length() <= 4){
                System.out.println(RED + "User Name must be at least 4 characters" + RESET);
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println(RED + "User Name Must contain at least 1 letter" + RESET);
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
                System.out.println(RED + "Product Name must be at least 4 characters" + RESET);
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println(RED +"Product Name Must contain at least 1 letter" + RESET);
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
                System.out.println(RED + "Price can't be less than or equal to 0" + RESET);
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
                System.out.println(RED + "Stock can't be less than 0" + RESET);
                continue;
        }
        isCorrect = true;
        return stock;
        }
        return -1;
    }

    public static Boolean isPasswordValid(String password){
        if(password.length() < 8){
            System.out.println(RED + "Password must be at least 8 characters" + RESET);
            return false;
        }

        if(!password.matches(".*[A-Z].*")){
            System.out.println(RED + "Password must contain at least 1 capital letter" + RESET);
            return false;
        }

        if(!password.matches(".*[a-z].*")){
            System.out.println(RED + "Password must contain at least 1 lowercase letter" + RESET);
            return false;
        }

        if(!password.matches(".*\\d.*")){
            System.out.println(RED + "Password must contain at least 1 number" + RESET);
            return false;
        }

        return true;
    }
    public static Boolean isPasswordCorrect(String password , User u){
        return u.getPassword().equals(password);
    }

    

    public static Boolean canBuy(double totalPrice){
        if(AppSession.currentUser.getMoney() < totalPrice){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
            return false;
        }
        return true;
    }



    //LISTS
    //Product List
    public void getAllProducts(){
        
        System.out.printf(YELLOW + "%-10s"+ CYAN +"%-15s" + GREEN +"%-10s"+ WHITE +"%-10s\n" + RESET, "Id" , "Name", "Price" , "Description\n");
        for(Product p: productList){
            if(p.getStock() > 0){
                System.out.printf(  YELLOW + "%-10d"+ CYAN +"%-15s" + GREEN +"%-10.2f"+ WHITE +"%-10s\n" + RESET, p.getId() , p.getName(), p.getPrice() ,
                p.getDescription());
            }
        }
    }

    //User's Product List
    public void getUsersProducts(){
        System.out.printf(YELLOW + "%-10s"+ CYAN +"%-15s"+ GREEN +"%-10s"+ MAGENTA +"%-10s"+ BLUE +"%-10s"+ WHITE +"%-10s\n"+ RESET,
         "Id" , "Name", "Price"  , "Stock" , "Total Purchases" , "Description\n");
        for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
            System.out.printf(YELLOW + "%-10d"+ CYAN +"%-20s"+ GREEN +"%-10.2f"+ MAGENTA +"%-10d"+ BLUE +"%-17d"+ WHITE +"%-10s\n"+ RESET,
            p.getId() , p.getName(), p.getPrice() , p.getStock()
            , p.getSellCount(),p.getDescription());
        }
    }

    //User's Cart List
    public void getUserCart(){
        double totalPrice = 0;
        System.out.printf(
        YELLOW + "%-10s" + CYAN + "%-20s" + GREEN + "%-10s" + MAGENTA + "%-10s\n" + RESET,
        "Id", "Name", "Price", "Amount");
        for(Cart c: carts.getCartProductsByUserId(AppSession.currentUserId)){
            Product prod = products.getProductById(c.getProductId());
            System.out.printf( YELLOW + "%-10d" + CYAN + "%-20s" + GREEN + "%-10.2f" + MAGENTA + "%-10d\n" + RESET, c.getProductId(),
            prod.getName(), prod.getPrice() , c.getAmount());
            totalPrice += prod.getPrice() * c.getAmount();
        }
        System.out.println("Total: " + totalPrice + " $");
    }

    //Purchase History
    public void getPurchaseHistory(){
        System.out.printf(
        YELLOW + "%-15s" + CYAN + "%-20s" + GREEN + "%-10s" + MAGENTA + "%-10s" + GREEN + "%-15s" +
        BLUE + "%-15s" + WHITE + "%-13s\n" + RESET,
        "Purchase Id", "Name", "Price", "Amount", "Total Price" ,"Seller", "Date");
        for(Purchase p: purchaseList){
            Product prod = products.getProductById(p.getProductId());
            double totalPrice = p.getAmount() * prod.getPrice();
            String sellerName = users.getUserById(p.getSellerId()).getName();
            System.out.printf(
            YELLOW + "%-15d" + CYAN + "%-20s" + GREEN + "%-10.2f" + MAGENTA + "%-10d" +
            GREEN + "%-15.2f" + BLUE + "%-15s" + WHITE + "%-13s\n" + RESET,
            p.getId(), prod.getName(), prod.getPrice(), p.getAmount(), totalPrice, sellerName, p.getDateString());
        }
    }

    //Favorites List
    public void getFavorites(){
        System.out.printf(YELLOW + "%-10s" + MAGENTA + "%-20s" + GREEN + "%-10s\n" + RESET, "Id" , "Name", "Price");
        for(Favorites f: favorites.getFavoritesByUserId(AppSession.currentUserId)){
            Product prod = products.getProductById(f.getProductId());
            System.out.printf(YELLOW + "%-10d" + MAGENTA + "%-20s" + GREEN + "%-10.2f\n" + RESET, f.getProductId(),
            prod.getName(), prod.getPrice());
        }
    }



    //BUY
    //Buy Product
    public void buyProduct(Product p , int amount){
        double price = p.getPrice() * amount;
        if(AppSession.currentUser.getMoney() < price){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
        }else if(p.getStock() < amount){
            System.out.println(YELLOW + "Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available" + RESET);
        }else{
            Purchase pur = new Purchase(amount, AppSession.currentUserId, p.getSellerId(), p.getId());
            pur.AddToDataBase();
            purchaseList.add(pur);
            p.SellProduct(amount);
            AppSession.currentUser.Pay(price);
            users.getUserById(p.getSellerId()).depositMoney(price);
            System.out.println(GREEN + "Purchase successful!" + RESET);
            System.out.println(GREEN +"New Balance: " + AppSession.currentUser.getMoney() + RESET);
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
                System.out.println(YELLOW + "Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available" + RESET);
                canBuy = false;
            }
        }
        if(totalPrice > AppSession.currentUser.getMoney()){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
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
            System.out.println(GREEN +"Successful! Items in your cart have been purchased." + RESET);
            System.out.println(RED +"Total Price: " + totalPrice + RESET);
            System.out.println(GREEN +"New Balance: " + AppSession.currentUser.getMoney() + RESET);
            }
    }

}
