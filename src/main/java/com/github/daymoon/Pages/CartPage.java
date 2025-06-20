package com.github.daymoon.Pages;

import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.Buy;
import com.github.daymoon.Lists;
import com.github.daymoon.Models.Cart;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class CartPage {

    public static void openPage(){
        int pagenumber = 6;
        AppSession.currentPage = pagenumber;
        while(true){
            int totalItemsInCart = ArrayLists.carts.getCartProductsByUserId(AppSession.currentUserId).size();
            System.out.println(CYAN + "=== CART ===" + RESET);
            if(totalItemsInCart == 0){
                System.out.println(RED + "Cart is Empty" + RESET);
                AppSession.previousPage = pagenumber;
                navigateToPage(2);
            }else{
                Lists.getUserCart();
                System.out.printf(YELLOW + "%-20s %-20s %-20s %-20s\n" + RESET , "1. Buy All" , "2. Remove All" , "3. Select Item" , "4. Exit");
                int input = ReadInput.readIntInput("Select an option: ");
                switch (input){
                    case 1:
                    while(true){
                        System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                        int option = ReadInput.readIntInput("Select an option: ");
                        switch (option){
                            case 1:
                            Buy.buyCart();
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
                    for(Cart c: ArrayLists.carts.getCartProductsByUserId(AppSession.currentUserId)){
                        c.DeleteFromDataBase();
                    }
                    if(totalItemsInCart == 0){
                        System.out.println(RED + "Cart Is Empty" + RESET);
                        break;
                    }
                    System.out.println(GREEN + "Products Successfully Deleted! From Cart" + RESET);
                    break;
                    case 3:
                    int i = ReadInput.readIntInput("Enter Product ID: ");
                    for(Cart c : ArrayLists.carts.getCartProductsByUserId(AppSession.currentUserId)){
                        Product prod = ArrayLists.products.getProductById(c.getProductId());
                        if(prod.getId() == i){
                            System.out.printf(YELLOW + "%-20s %-20s %-20s\n" + RESET ,"1. Buy" , "2.Remove" , "3.Exit");
                            int inp = ReadInput.readIntInput("Select and option: ");
                            switch (inp){
                                case 1:
                                while(true){
                                    System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                                    int option = ReadInput.readIntInput("Select an option: ");
                                    switch (option){
                                        case 1:
                                        Buy.buyProduct(prod, c.getAmount());
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
                                int amount = ReadInput.readIntInput("Enter amount to delete: ");
                                if(c.getAmount() <= amount){
                                    c.DeleteFromDataBase();
                                    ArrayLists.cartList.remove(c);
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
                    navigateToPage(2);
                    return;
                    default:
                    System.out.println(RED + "Please select valid option" + RESET);
                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                    break;
                }
            }
            
        }
    }
}
