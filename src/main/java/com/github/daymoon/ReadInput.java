package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;

import com.github.daymoon.Utils.AppSession;

public class ReadInput {
    
    private static String readInput(String prompt){
        System.out.print(BLUE + prompt + RESET);
        String input = sc.nextLine().trim();
        switch (input.toLowerCase()){
            case "help":
            printHelp();
            return readInput(prompt);
            case "exit":
            System.exit(0);
            break;
            case "logout":
            if(AppSession.currentUser == null){
                System.out.println(RED + "You haven't logged in yet" + RESET);
                break;
            }
            System.out.println(RED + "Logging out..." + RESET);
            AppSession.currentUser=null;
            AppSession.currentUserId = -1;
            navigateToPage(1);
            break;
        case "back":
           if (AppSession.currentUser == null) {
                System.out.println(RED + "You haven't logged in yet" + RESET);
                return readInput(prompt);
            } else {
                if (!navigateToPage(AppSession.previousPage)) {
                    System.out.println(RED + "You can't go to previous page." + RESET);
                    return readInput(prompt);
                }
            }
            return "";
        case "cancel":
            if (!navigateToPage(AppSession.currentPage)) {
                System.out.println(RED + "There is no current page." + RESET);
            }
            return "";
        case "menu":
            return goToIfLoggedIn(2); // mainPage

        case "market":
            return goToIfLoggedIn(3); // marketPage

        case "profile":
            return goToIfLoggedIn(4); // profilePage

        case "wallet":
            return goToIfLoggedIn(5); // walletPage

        case "cart":
            return goToIfLoggedIn(6); // cartPage

        case "fav":
        case "favorites":
            return goToIfLoggedIn(9); // favoritesPage

        case "purch":
        case "purchases":
            return goToIfLoggedIn(7); // purchasesPage

        case "add":
            return goToIfLoggedIn(8); // addProductPage
        case "user":
            System.out.println(AppSession.currentUser);
            readInput(prompt);
            break;
    }

    return input;
    }

    private static int readIntInput(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                return Integer.parseInt(input);
            }catch (NumberFormatException e) {
                System.out.println( RED + "Please enter a valid number:" + RESET);
            }
        }
    }
    
    private static double readDoubleInput(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                return Double.parseDouble(input);
            }catch (NumberFormatException e) {
                    System.out.println( RED + "Please enter a valid number:" + RESET);

            }
        }
    }
}
