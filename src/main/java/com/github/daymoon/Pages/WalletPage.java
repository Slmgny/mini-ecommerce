package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;

import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class WalletPage {
    
    static int pagenumber = 5;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        while(true){
            System.out.println(CYAN + "=== Wallet ===" + RESET);
            System.out.println(GREEN + "Balance: " + AppSession.currentUser.getMoney() + RESET);
            System.out.println(YELLOW + "1." + RESET +"Deposit Money");
            System.out.println(YELLOW + "2."+ RESET +"Main Menu");
            int input = ReadInput.readIntInput("Select an option: ");
            switch (input){
                case 1:
                double deposit = ReadInput.readIntInput("Deposit amount: ");
                AppSession.currentUser.depositMoney(deposit);
                AppSession.currentUser.updateUser(AppSession.currentUser.getName(), AppSession.currentUser.getPassword(), AppSession.currentUser.getMoney());
                continue;
                case 2:
                AppSession.previousPage = pagenumber;
                navigateToPage(MainPage.pagenumber);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
        }
    }
}
