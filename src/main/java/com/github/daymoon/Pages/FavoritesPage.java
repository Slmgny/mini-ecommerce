package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import java.util.Iterator;

import static com.github.daymoon.Navigate.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.Lists;
import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

import com.github.daymoon.Models.*;

public class FavoritesPage {

    static int pagenumber = 9;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        int favotiresCount = ArrayLists.favorites.getFavoritesByUserId(AppSession.currentUserId).size();
        if(favotiresCount == 0){
            System.out.println(RED + "Favorites empty" + RESET);
            navigateToPage(AppSession.previousPage);
            return;
        }
        while(true){
            System.out.println(CYAN + "=== FAVORITES ===" + RESET);
            Lists.getFavorites();
            System.out.println("");
            System.out.printf(YELLOW +"%-20s %-20s %-20s \n" + RESET , "1. Select Item" , "2. Remove All" , "3. Add All to Cart");
            int input = ReadInput.readIntInput("Select an option: ");
            switch(input){
                case 1:
                while(true){
                    int i = ReadInput.readIntInput("Enter Product ID: ");
                    for(Favorites f: ArrayLists.favorites.getFavoritesByUserId(AppSession.currentUserId)){
                        if(i == f.getProductId()){
                            Product prod = ArrayLists.products.getProductById(f.getProductId());
                            System.out.printf(YELLOW + "%-10d" + MAGENTA + "%-20s" + GREEN + "%-10.2f\n" + RESET, f.getProductId(),
                            prod.getName(), prod.getPrice());;
                            while(true){
                                System.out.printf(YELLOW + "%-20s , %-20s\n" + RESET , " 1. Add to Cart " , " 2. Remove from Favorites ");
                                int option = ReadInput.readIntInput("Select an option: ");
                                switch (option){
                                    case 1:
                                    int amount = ReadInput.readIntInput("Enter the amount: ");
                                    Cart c = new Cart(AppSession.currentUserId, f.getProductId(), amount);
                                    c.AddToDataBase();
                                    ArrayLists.cartList.add(c);
                                    System.out.println(GREEN + "Added to cart." + RESET);
                                    navigateToPage(9);
                                    break;
                                    case 2:
                                    f.DeleteFromDataBase();
                                    ArrayLists.favoritesList.remove(f);
                                    System.out.println(GREEN + "Removed from favorites." + RESET);
                                    navigateToPage(9);
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
                for(Favorites f: ArrayLists.favorites.getFavoritesByUserId(AppSession.currentUserId)){
                    f.DeleteFromDataBase();
                    ArrayLists.favoritesList.remove(f);
                }
                navigateToPage(pagenumber);
                break;
                case 3:
                for(Favorites f: ArrayLists.favorites.getFavoritesByUserId(AppSession.currentUserId)){
                    boolean found = false;
                    Product prod = ArrayLists.products.getProductById(f.getProductId());
                    int amount = ReadInput.readIntInput("Enter amount for " + prod.getName() + ": ");
                    Iterator<Cart> iter = ArrayLists.cartList.iterator();
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
                        ArrayLists.cartList.add(c);
                    }
                }
                AppSession.previousPage = pagenumber;
                navigateToPage(CartPage.pagenumber);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                break;
            }
        }
    }
}
