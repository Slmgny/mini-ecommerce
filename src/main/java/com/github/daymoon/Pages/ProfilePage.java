package com.github.daymoon.Pages;
import static com.github.daymoon.Utils.TextColors.*;
import static com.github.daymoon.Navigate.*;

import com.github.daymoon.GetInfo;
import com.github.daymoon.Validate;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

import com.github.daymoon.Models.*;

public class ProfilePage {

    static int pagenumber = 4;
    public static void openPage(){
        
        AppSession.currentPage = pagenumber;
        User user = AppSession.currentUser;
        while(true){
            System.out.println(CYAN + "=== PROFILE ===" + RESET);
            System.out.println(YELLOW + "1." + RESET + " Edit Your Name");
            System.out.println(YELLOW + "2." + RESET + " Change Password");
            System.out.println(YELLOW + "3." + RESET + " View Your Products");
            System.out.println(YELLOW + "4." + RESET + " View Your Favorites");
            int input = ReadInput.readIntInput("Select an option: ");
            switch (input){
                case 1:
                String password = ReadInput.readInput("Enter Your Password: ");
                while(!Validate.isPasswordCorrect(password , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password = ReadInput.readInput("Enter Your Password: ");
                    
                }
                String newName = GetInfo.askName();
                System.out.println(GREEN + "Name updated successfully!" + RESET);
                user.setName(newName);
                user.updateUser(newName, user.getPassword(), user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 2:
                String password2 = ReadInput.readInput("Enter Your Password: ");
                while(!Validate.isPasswordCorrect(password2 , AppSession.currentUser)){
                    System.out.println(RED + "Wrong Password!" + RESET);
                    password2 = ReadInput.readInput("Enter Your Password: ");
                }
                String newPassword = ReadInput.readInput("Enter Your New Password: ");
                while(!Validate.isPasswordValid(newPassword)){
                    newPassword = ReadInput.readInput("Enter Your New Password: ");
                }
                user.setPassword(newPassword);
                System.out.println(GREEN + "Password updated successfully!" + RESET);
                user.updateUser(user.getName(), newPassword, user.getMoney());
                AppSession.previousPage = pagenumber;
                continue;
                case 3:
                navigateToPage(UserProductsPage.pagenumber);
                break;
                case 4:
                AppSession.previousPage = pagenumber;
                navigateToPage(FavoritesPage.pagenumber);
                break;
                default:
                System.out.println(RED + "Please select valid option" + RESET);
                System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
                break;
            }
        }
    }
}
