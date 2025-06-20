package com.github.daymoon.Pages;

import com.github.daymoon.AppSession;
import com.github.daymoon.Cart;
import com.github.daymoon.Product;

public class CartPage {
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
                    while(true){
                        System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                        int option = readIntInput("Select an option: ");
                        switch (option){
                            case 1:
                            buyCart();
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
                    for(Cart c: carts.getCartProductsByUserId(AppSession.currentUserId)){
                        c.DeleteFromDataBase();
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
                                while(true){
                                    System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                                    int option = readIntInput("Select an option: ");
                                    switch (option){
                                        case 1:
                                        buyProduct(prod, c.getAmount());
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
}
