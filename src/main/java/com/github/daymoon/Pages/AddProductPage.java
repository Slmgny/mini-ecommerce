package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.GetInfo;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class AddProductPage {
    
    static int pagenumber = 8;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        String pName;
        String pDesc;
        double pPrice;
        int pStock;

        System.out.println(CYAN + "=== ADD PRODUCT ===" + RESET);
        System.out.println(YELLOW + "You have to enter your Products Name , Price , Stock and (Optional) Description" + RESET);
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);

        while(true){
            pName = GetInfo.askName();
            pPrice = GetInfo.askPrice();
            pStock = GetInfo.askStock();
            pDesc = ReadInput.readInput("(Optional) Description: ");
            
            Product product = new Product(pName, pPrice, pStock, pDesc);
            product.setSellerId(AppSession.currentUserId);
            product.AddToDataBase();
            ArrayLists.productList.add(product);
            System.out.println("Product Added!");
            ReadInput.readInput(YELLOW + "Type " + MAGENTA  + "'menu' " + YELLOW +"to go back to the main menu or press Enter to add another product: "+ RESET);
        }
    }
}
