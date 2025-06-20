package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;

public class AddProductPage {
    //Add Product Page
    public void openPage(){
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
