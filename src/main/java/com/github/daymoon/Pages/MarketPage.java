package com.github.daymoon.Pages;

import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Favorites;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;

public class MarketPage {
    
    //Market Page
    public void openPage(){
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
                        while(true){
                            System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                            int option = readIntInput("Select an option: ");
                            switch (option){
                                case 1:
                                buyProduct(p, pAmount);
                                break;
                                case 2:
                                break;
                                default:
                                System.out.println("Please select valid option");
                                continue;
                            }
                            break;
                        }
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
}
