package com.github.daymoon.Utils;
import static com.github.daymoon.Utils.TextColors.*;

public class HelpPrinter {
    public static void printHelp(){
        System.out.println("Type " + MAGENTA + "'back'" + RESET + " to go to the " + YELLOW + "previous page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'exit'" + RESET + " to " + YELLOW + "close the app" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'logout'" + RESET + " to " + YELLOW + "logut" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'cancel'" + RESET + " to " + YELLOW + "restart this page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'profile'" + RESET + " to go to " + YELLOW + "your profile" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'market'" + RESET + " to go to the " + YELLOW + "market page" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'menu'" + RESET + " to go to the " + YELLOW + "main menu" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'wallet'" + RESET + " to go to " + YELLOW + "your wallet" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'cart'" + RESET + " to view " + YELLOW + "your cart" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'fav'" + RESET + " or " + MAGENTA + "'favorites'" + RESET + " to view " + YELLOW + "your favorites" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'purch'" + RESET + " or " + MAGENTA + "'purchases'" + RESET + " to view " + YELLOW + "your purchases" + RESET + ".");
        System.out.println("Type " + MAGENTA + "'add'" + RESET + " to go to the " + YELLOW + "add product page" + RESET + ".");

    }
}
