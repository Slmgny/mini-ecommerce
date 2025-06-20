package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import java.util.Iterator;

import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Favorites;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;

public class FavoritesPage {
    //Favorites Page
    public void openPage(){
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
}
