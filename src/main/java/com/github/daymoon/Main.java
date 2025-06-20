package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.github.daymoon.DAO.CartDAO;
import com.github.daymoon.DAO.FavoritesDAO;
import com.github.daymoon.DAO.ProductDAO;
import com.github.daymoon.DAO.PurchaseDAO;
import com.github.daymoon.DAO.UserDAO;
import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Favorites;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Models.Purchase;
import com.github.daymoon.Models.User;
import com.github.daymoon.Utils.AppSession;

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


    private void init(){

        userList.addAll(users.getAllUsers());
        productList.addAll(products.getAllProducts());
        purchaseList.addAll(purchases.getAllPurchases());
        cartList.addAll(carts.getAllCarts());
        favoritesList.addAll(favorites.getAllFavorites());

    }
    
    public static void main(String[] args) {
        Main app = new Main();
        app.init();
        System.out.println();
        app.run();

        
    }
    
    

    
    public void run(){
        while(true){
            System.out.println(BLUE + "Welcome to Mini E-Commerce App" + RESET);
            
        }
    }

}
