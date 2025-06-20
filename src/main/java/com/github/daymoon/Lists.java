package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.*;


import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Favorites;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Models.Purchase;
import com.github.daymoon.Utils.AppSession;

public class Lists {

    public static void getUsersProducts(){
        System.out.printf(YELLOW + "%-10s"+ CYAN +"%-15s"+ GREEN +"%-10s"+ MAGENTA +"%-10s"+ BLUE +"%-10s"+ WHITE +"%-10s\n"+ RESET,
         "Id" , "Name", "Price"  , "Stock" , "Total Purchases" , "Description\n");
        for(Product p: ArrayLists.products.getAllProductsBySellerId(AppSession.currentUserId)){
            System.out.printf(YELLOW + "%-10d"+ CYAN +"%-20s"+ GREEN +"%-10.2f"+ MAGENTA +"%-10d"+ BLUE +"%-17d"+ WHITE +"%-10s\n"+ RESET,
            p.getId() , p.getName(), p.getPrice() , p.getStock()
            , p.getSellCount(),p.getDescription());
        }
    }
    public static void getPurchaseHistory(){
        System.out.printf(
        YELLOW + "%-15s" + CYAN + "%-20s" + GREEN + "%-10s" + MAGENTA + "%-10s" + GREEN + "%-15s" +
        BLUE + "%-15s" + WHITE + "%-13s\n" + RESET,
        "Purchase Id", "Name", "Price", "Amount", "Total Price" ,"Seller", "Date");
        for(Purchase p: ArrayLists.purchaseList){
            Product prod = ArrayLists.products.getProductById(p.getProductId());
            double totalPrice = p.getAmount() * prod.getPrice();
            String sellerName = ArrayLists.users.getUserById(p.getSellerId()).getName();
            System.out.printf(
            YELLOW + "%-15d" + CYAN + "%-20s" + GREEN + "%-10.2f" + MAGENTA + "%-10d" +
            GREEN + "%-15.2f" + BLUE + "%-15s" + WHITE + "%-13s\n" + RESET,
            p.getId(), prod.getName(), prod.getPrice(), p.getAmount(), totalPrice, sellerName, p.getDateString());
        }
    }
    public static void getAllProducts(){
        
        System.out.printf(YELLOW + "%-10s"+ CYAN +"%-15s" + GREEN +"%-10s"+ WHITE +"%-10s\n" + RESET, "Id" , "Name", "Price" , "Description\n");
        for(Product p: ArrayLists.productList){
            if(p.getStock() > 0){
                System.out.printf(  YELLOW + "%-10d"+ CYAN +"%-15s" + GREEN +"%-10.2f"+ WHITE +"%-10s\n" + RESET, p.getId() , p.getName(), p.getPrice() ,
                p.getDescription());
            }
        }
    }
    public static void getFavorites(){
        System.out.printf(YELLOW + "%-10s" + MAGENTA + "%-20s" + GREEN + "%-10s\n" + RESET, "Id" , "Name", "Price");
        for(Favorites f: ArrayLists.favorites.getFavoritesByUserId(AppSession.currentUserId)){
            Product prod = ArrayLists.products.getProductById(f.getProductId());
            System.out.printf(YELLOW + "%-10d" + MAGENTA + "%-20s" + GREEN + "%-10.2f\n" + RESET, f.getProductId(),
            prod.getName(), prod.getPrice());
        }
    }
    public static void getUserCart(){
        double totalPrice = 0;
        System.out.printf(
        YELLOW + "%-10s" + CYAN + "%-20s" + GREEN + "%-10s" + MAGENTA + "%-10s\n" + RESET,
        "Id", "Name", "Price", "Amount");
        for(Cart c: ArrayLists.carts.getCartProductsByUserId(AppSession.currentUserId)){
            Product prod = ArrayLists.products.getProductById(c.getProductId());
            System.out.printf( YELLOW + "%-10d" + CYAN + "%-20s" + GREEN + "%-10.2f" + MAGENTA + "%-10d\n" + RESET, c.getProductId(),
            prod.getName(), prod.getPrice() , c.getAmount());
            totalPrice += prod.getPrice() * c.getAmount();
        }
        System.out.println("Total: " + totalPrice + " $");
    }

}
