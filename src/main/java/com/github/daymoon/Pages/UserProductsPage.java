package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Models.Product;
import com.github.daymoon.Utils.AppSession;

public class UserProductsPage {
    public void openPage(){
        int pagenumber = 10;
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "===YOUR PRODUCTS===" + RESET);
        while(true){
            getUsersProducts();
            System.out.printf(YELLOW + "\n%-20s %-20s\n" + RESET , " 1. Edit Product " , " 2. Go Back ");
            int i = readIntInput("Select an option: ");
            switch (i){
                case 1:
                int inp = readIntInput("Enter Product ID: ");
                boolean found = false;
                for(Product p: products.getAllProductsBySellerId(AppSession.currentUserId)){
                    if(inp == p.getId()){
                        found = true;
                        while(true){
                            System.out.printf(YELLOW + "%-10d"+ CYAN +"%-20s"+ GREEN +"%-10.2f"+ MAGENTA +"%-10d"+ BLUE +"%-17d"+ WHITE +"%-10s\n"+ RESET,
                            p.getId() , p.getName(), p.getPrice() , p.getStock(),
                            p.getSellCount(),p.getDescription());
                            System.out.printf(YELLOW  + "%-20s %-20s %-20s %-20s %-20s %-20s\n" + RESET , " 1. Change Name " , " 2. Change Price " 
                            , " 3. Change Stock ", " 4. Change Description " , " 5. Delete Product" , " 6. Exit");
                            int option = readIntInput("Select an option: ");
                            switch (option){
                                case 1:
                                String newProductName = AskProductName();
                                p.setName(newProductName);
                                p.updateProduct(newProductName, p.getPrice(), p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 2:
                                double newPrice = AskPrice();
                                p.setPrice(newPrice);
                                p.updateProduct(p.getName(), newPrice, p.getSellCount() , p.getStock(), p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 3:
                                int stock = AskStock();
                                p.setStock(stock);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , stock , p.getDescription());
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 4:
                                String desc = readInput("Enter new Description");
                                p.setDescription(desc);
                                p.updateProduct(p.getName(), p.getPrice(), p.getSellCount() , p.getStock() , desc);
                                AppSession.previousPage = pagenumber;
                                profilePage();
                                break;
                                case 5:
                                p.DeleteFromDataBase();
                                productList.remove(p);
                                System.out.println(GREEN + "Successfuly Deleted the Product" + RESET);
                                navigateToPage(pagenumber);
                                case 6:
                                AppSession.previousPage = pagenumber;
                                profilePage();
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
                profilePage();
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
