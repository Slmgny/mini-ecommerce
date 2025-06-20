package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Models.User;
import com.github.daymoon.Utils.AppSession;

public class ProfilePage {
    public void openPage(){
        int pagenumber = 4;
        AppSession.currentPage = pagenumber;
        User user = AppSession.currentUser;
        while(true){
            System.out.println(CYAN + "=== PROFILE ===" + RESET);
            System.out.println(YELLOW + "1." + RESET + " Edit Your Name");
            System.out.println(YELLOW + "2." + RESET + " Change Password");
            System.out.println(YELLOW + "3." + RESET + " View Your Products");
            System.out.println(YELLOW + "4." + RESET + " View Your Favorites");
            int input = readIntInput("Select an option: ");
            switch (input){
                case 1:
                String password = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password = readInput("Enter Your Password: ");
                    
                }
                String newName = AskName();
                System.out.println(GREEN + "Name updated successfully!" + RESET);
                user.setName(newName);
                user.updateUser(newName, user.getPassword(), user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 2:
                String password2 = readInput("Enter Your Password: ");
                while(!isPasswordCorrect(password2 , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password2 = readInput("Enter Your Password: ");
                }
                String newPassword = readInput("Enter Your New Password: ");
                while(!isPasswordValid(newPassword)){
                    newPassword = readInput("Enter Your New Password: ");
                }
                user.setPassword(newPassword);
                System.out.println(GREEN + "Password updated successfully!" + RESET);
                user.updateUser(user.getName(), newPassword, user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 3:
                userProductsPage();
                break;
                case 4:
                AppSession.previousPage = pagenumber;
                favoritesPage();
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
        }
    }
}
