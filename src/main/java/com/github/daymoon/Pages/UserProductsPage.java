package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.GetInfo;
import com.github.daymoon.Lists;
import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class UserProductsPage {

    static int pagenumber = 10;
    public static void openPage(){    
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "===YOUR PRODUCTS===" + RESET);
        while(true){
            Lists.getUsersProducts();
            System.out.printf(YELLOW + "\n%-20s %-20s\n" + RESET , " 1. Edit Product " , " 2. Go Back ");
            int i = ReadInput.readIntInput("Select an option: ");
            switch (i){
                case 1:
                int inp = ReadInput.readIntInput("Enter Product ID: ");
                boolean found = false;
                for(Product p: ArrayLists.products.getAllProductsBySellerId(AppSession.currentUserId)){
                    if(inp == p.getId()){
                        found = true;
                        while(true){
                            System.out.printf(YELLOW + "%-10d"+ CYAN +"%-20s"+ GREEN +"%-10.2f"+ MAGENTA +"%-10d"+ BLUE +"%-17d"+ WHITE +"%-10s\n"+ RESET,
                            p.getId() , p.getName(), p.getPrice() , p.getStock(),
                            p.getSellCount(),p.getDescription());
                            System.out.printf(YELLOW  + "%-20s %-20s %-20s %-20s %-20s %-20s\n" + RESET , " 1. Change Name " , " 2. Change Price " 
                            , " 3. Change Stock ", " 4. Change Description " , " 5. Delete Product" , " 6. Exit");
                            int option = ReadInput.readIntInput("Select an option: ");
                            switch (option){
                                case 1:
                                String newProductName = GetInfo.askProductName();
                                p.setName(newProductName);
                                p.updateProduct(newProductName, p.getPrice(), p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                navigateToPage(ProfilePage.pagenumber);
                                break;
                                case 2:
                                double newPrice = GetInfo.askPrice();
                                p.setPrice(newPrice);
                                p.updateProduct(p.getName(), newPrice, p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                navigateToPage(ProfilePage.pagenumber);
                                break;
                                case 3:
                                int stock = GetInfo.askStock();
                                p.setStock(stock);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , stock , p.getDescription());
                                AppSession.previousPage = pagenumber;
                                navigateToPage(ProfilePage.pagenumber);
                                break;
                                case 4:
                                String desc = ReadInput.readInput("Enter new Description");
                                p.setDescription(desc);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , p.getStock() , desc);
                                AppSession.previousPage = pagenumber;
                                navigateToPage(ProfilePage.pagenumber);
                                break;
                                case 5:
                                p.DeleteFromDataBase();
                                ArrayLists.productList.remove(p);
                                System.out.println(GREEN + "Successfuly Deleted the Product" + RESET);
                                navigateToPage(pagenumber);
                                case 6:
                                AppSession.previousPage = pagenumber;
                                navigateToPage(ProfilePage.pagenumber);
                                break;
                                default:
                                System.out.println(RED + "Please select valid option" + RESET);
                                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                                break;
                            }
                        }  
                    }
                }
                if(!found){
                    System.out.println(RED + "Product not found" + RESET);
                    break;
                }
                break;
                case 2:
                AppSession.previousPage = pagenumber;
                navigateToPage(ProfilePage.pagenumber);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
            break;
        }
    }
}
