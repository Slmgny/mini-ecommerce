package com.github.daymoon.Pages;

import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;


import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class MainPage {

    static int pagenumber = 2;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "=== MAIN MENU ===" + RESET);
        System.out.println(YELLOW + "1." + RESET + " Market");
        System.out.println(YELLOW + "2." + RESET + " Profile");
        System.out.println(YELLOW + "3." + RESET + " Wallet");
        System.out.println(YELLOW + "4." + RESET + " Cart");
        System.out.println(YELLOW + "5." + RESET + " Purchases");
        System.out.println(YELLOW + "6." + RESET + " Add Your Product");
        System.out.println(RED + "7." + RESET + " Logout");
        System.out.println(RED + "8." + RESET + " Exit");
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);

        while(true){
        String input = ReadInput.readInput(CYAN + "Select an option: " + RESET);
            switch (input){
                case "1":
                AppSession.previousPage = pagenumber;
                navigateToPage(MarketPage.pagenumber);
                break;
                case "2":
                AppSession.previousPage = pagenumber;
                navigateToPage(ProfilePage.pagenumber);
                break;
                case "3":
                AppSession.previousPage = pagenumber;
                navigateToPage(WalletPage.pagenumber);
                break;
                case "4":
                AppSession.previousPage = pagenumber;
                navigateToPage(CartPage.pagenumber);
                break;
                case "5":
                AppSession.previousPage = pagenumber;
                navigateToPage(PurchasesPage.pagenumber);
                break;
                case "6":
                AppSession.previousPage = pagenumber;
                navigateToPage(AddProductPage.pagenumber);
                break;
                case "7":
                AppSession.previousPage = 1;
                System.out.println(RED + "Logging out..." + RESET);
                AppSession.currentUser=null;
                AppSession.currentUserId = -1;
                navigateToPage(AuthPage.pagenumber);
                break;
                case "8":
                System.exit(0);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                break;

            }
        }
    }
}
