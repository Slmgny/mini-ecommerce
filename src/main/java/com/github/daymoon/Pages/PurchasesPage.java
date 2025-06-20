package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import java.util.Iterator;

import static com.github.daymoon.Navigate.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.Lists;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

import com.github.daymoon.Models.*;


public class PurchasesPage {

    static int pagenumber = 7;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        boolean found = false;
        while(true){
            System.out.println(CYAN + "=== PURCHASE HISTORY ===" + RESET);
            int purchaseCount = ArrayLists.purchases.getAllPurchasesByUserId(AppSession.currentUserId).size();
            if(purchaseCount > 0){
                Lists.getPurchaseHistory();
                System.out.printf(YELLOW + "%-20s %-20s\n" + RESET , " 1. Request Refund: " , " 2. Main Menu");;
                int input = ReadInput.readIntInput("Select an option: ");
                switch(input){
                    case 1:
                    while(true){
                        int i = ReadInput.readIntInput("Enter Purchase ID: ");
                        Iterator <Purchase> iterator = ArrayLists.purchaseList.iterator(); 
                        while (iterator.hasNext()) {
                            Purchase pur = iterator.next();
                            if (pur.getId() == i) {
                                System.out.printf(GREEN + "%-20s" + RED + "%-20s\n" + RESET, " 1. Confirm ", " 2. Cancel");
                                int option = ReadInput.readIntInput("Select an option: ");
                                switch (option) {
                                    case 1:
                                        double price = ArrayLists.products.getProductById(pur.getProductId()).getPrice() * pur.getAmount();
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
                    navigateToPage(MainPage.pagenumber);
                    return;
                    default:
                    System.out.println(RED + "Please select valid option" + RESET);
                    System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                    break;
            }
            }else{
                System.out.println(RED +"Purhcase History is Empty"  + RESET);
                AppSession.previousPage = pagenumber;
                navigateToPage(MainPage.pagenumber);
            }
        }
    }
}
