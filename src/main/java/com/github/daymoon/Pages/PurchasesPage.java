package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import java.util.Iterator;

import com.github.daymoon.Models.Purchase;
import com.github.daymoon.Utils.AppSession;

public class PurchasesPage {
    public void openPage(){
        int pagenumber = 7;
        AppSession.currentPage = pagenumber;
        boolean found = false;
        while(true){
            System.out.println(CYAN + "=== PURCHASE HISTORY ===" + RESET);
            int purchaseCount = purchases.getAllPurchasesByUserId(AppSession.currentUserId).size();
            if(purchaseCount > 0){
                getPurchaseHistory();
                System.out.printf(YELLOW + "%-20s %-20s\n" + RESET , " 1. Request Refund: " , " 2. Main Menu");;
                int input = readIntInput("Select an option: ");
                switch(input){
                    case 1:
                    while(true){
                        int i = readIntInput("Enter Purchase ID: ");
                        Iterator <Purchase> iterator = purchaseList.iterator(); 
                        while (iterator.hasNext()) {
                            Purchase pur = iterator.next();
                            if (pur.getId() == i) {
                                System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                                int option = readIntInput("Select an option: ");
                                switch (option) {
                                    case 1:
                                        double price = products.getProductById(pur.getProductId()).getPrice() * pur.getAmount();
                                        AppSession.currentUser.depositMoney(price);
                                        iterator.remove();
                                        pur.DeleteFromDataBase();
                                        System.out.println(GREEN + "Refund completed." + RESET);
                                        break;
                                    case 2:
                                        System.out.println(YELLOW + "Cancelled." + RESET);
                                        break;
                                    default:
                                        System.out.println(RED + "Please select a valid option." + RESET);
                                        break;
                                }
                                found = true;
                                break;
                            }
                        }
                    if (!found) {
                    System.out.println(RED + "Purchase not found." + RESET);
                    }
                    break;
                    }
                    case 2:
                    AppSession.previousPage = pagenumber;
                    mainPage();
                    return;
                    default:
                    System.out.println(RED + "Please select valid option" + RESET);
                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                    break;
            }
            }else{
                System.out.println(RED +"Purhcase History is Empty"  + RESET);
                AppSession.previousPage = pagenumber;
                mainPage();
            }
        }
    }
}
