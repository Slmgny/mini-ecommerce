package com.github.daymoon.Pages;

import com.github.daymoon.AppSession;
import com.github.daymoon.Product;

public class AddProductPage {
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
}
