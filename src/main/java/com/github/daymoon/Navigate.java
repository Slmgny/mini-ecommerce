package com.github.daymoon;
import static com.github.daymoon.Utils.TextColors.*;

public class Navigate {
    public static boolean navigateToPage(int pageNumber) {
        AppSession.previousPage = AppSession.currentPage;
        AppSession.currentPage = pageNumber;

        switch (pageNumber) {
            case 1: LoginOrSignUpPage(); break;
            case 2: mainPage(); break;
            case 3: marketPage(); break;
            case 4: profilePage(); break;
            case 5: walletPage(); break;
            case 6: cartPage(); break;
            case 7: purchasesPage(); break;
            case 8: AddProductPage(); break;
            case 9: favoritesPage(); break;
            default: return false;
        }
            return true;
    }
    public static String goToIfLoggedIn(int pageNumber) {
        if (AppSession.currentUser == null) {
            System.out.println(RED + "You haven't logged in yet" + RESET);
            return readInput(">> ");
        }
        navigateToPage(pageNumber);
        return "";
    }
}
