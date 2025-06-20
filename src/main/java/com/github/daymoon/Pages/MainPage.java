package com.github.daymoon.Pages;

import static com.github.daymoon.TextColors.*;

import com.github.daymoon.AppSession;

public class MainPage {
    //Main Page
    public void mainPage(){
        int pagenumber = 2;
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
        String input = readInput(CYAN + "Select an option: " + RESET);
            switch (input){
                case "1":
                AppSession.previousPage = pagenumber;
                marketPage();
                break;
                case "2":
                AppSession.previousPage = pagenumber;
                profilePage();
                break;
                case "3":
                AppSession.previousPage = pagenumber;
                walletPage();
                break;
                case "4":
                AppSession.previousPage = pagenumber;
                cartPage();
                break;
                case "5":
                AppSession.previousPage = pagenumber;
                purchasesPage();
                break;
                case "6":
                AppSession.previousPage = pagenumber;
                AddProductPage();
                break;
                case "7":
                AppSession.previousPage = 1;
                System.out.println(RED + "Logging out..." + RESET);
                AppSession.currentUser=null;
                AppSession.currentUserId = -1;
                LoginOrSignUpPage();
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
