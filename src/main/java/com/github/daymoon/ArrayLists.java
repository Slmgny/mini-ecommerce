package com.github.daymoon;

import java.util.ArrayList;

import com.github.daymoon.DAO.*;
import com.github.daymoon.Models.*;

public class ArrayLists {
    public static UserDAO users = new UserDAO();
    public static ProductDAO products = new ProductDAO();
    public static PurchaseDAO purchases = new PurchaseDAO();
    public static CartDAO carts = new CartDAO();
    public static FavoritesDAO favorites = new FavoritesDAO();


    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<Product> productList = new ArrayList<>();
    public static ArrayList<Purchase> purchaseList = new ArrayList<>();
    public static ArrayList<Cart> cartList = new ArrayList<>();
    public static ArrayList<Favorites> favoritesList = new ArrayList<>();


    public static void init(){

        userList.addAll(users.getAllUsers());
        productList.addAll(products.getAllProducts());
        purchaseList.addAll(purchases.getAllPurchases());
        cartList.addAll(carts.getAllCarts());
        favoritesList.addAll(favorites.getAllFavorites());

    }
}
