package com.github.daymoon;

import com.github.daymoon.Pages.*;
import com.github.daymoon.Utils.*;

import static com.github.daymoon.Utils.TextColors.*;


public class Navigate {
    public static boolean navigateToPage(int pageNumber) {
        AppSession.previousPage = AppSession.currentPage;
        AppSession.currentPage = pageNumber;

        switch (pageNumber) {
            case 1: AuthPage.openPage(); break;
            case 2: MainPage.openPage(); break;
            case 3: MarketPage.openPage(); break;
            case 4: ProfilePage.openPage(); break;
            case 5: WalletPage.openPage(); break;
            case 6: CartPage.openPage(); break;
            case 7: PurchasesPage.openPage(); break;
            case 8: AddProductPage.openPage(); break;
            case 9: FavoritesPage.openPage(); break;
            default: return false;
        }
            return true;
    }
    public static String goToIfLoggedIn(int pageNumber) {
        if (AppSession.currentUser == null) {
            System.out.println(RED + "You haven't logged in yet" + RESET);
            return ReadInput.readInput(">> ");
        }
        navigateToPage(pageNumber);
        return "";
    }
}
