package com.github.daymoon.Pages;

import static com.github.daymoon.Navigate.*;
import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.ArrayLists;
import com.github.daymoon.GetInfo;
import com.github.daymoon.Validate;
import com.github.daymoon.Models.MarketUser;
import com.github.daymoon.Models.User;
import com.github.daymoon.Utils.AppSession;
import com.github.daymoon.Utils.ReadInput;

public class AuthPage {
    
    static int pagenumber = 1;
    public static void openPage(){
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "=== LOG IN OR SIGN UP ===" + RESET);
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
        String userName = GetInfo.askName();
        boolean newUser = true;
        User currentUser = null;
        for(User u: ArrayLists.userList){
            if(u.getName().equals(userName)){
                newUser = false;
                currentUser = u;
                break;
            }
        }
        if(newUser){ // Sign up
            System.out.println(CYAN + "=== SIGN UP ===" + RESET);
            String password = ReadInput.readInput("Create Your Password: ");
            while(!Validate.isPasswordValid(password)){
                password = ReadInput.readInput("Create Your Password: ");
            }
            User user = new MarketUser(userName , password , 1 , 0);
            user.AddToDataBase();
            ArrayLists.userList.add(user);
            AppSession.currentUser = user;
            AppSession.currentUserId = user.getId();
            AppSession.previousPage = pagenumber;
            navigateToPage(2);
        }else{ // Login
            System.out.println(CYAN + "=== LOG IN ===" + RESET);
            String password = ReadInput.readInput("Enter Your Password: ");
            while(!Validate.isPasswordCorrect(password , currentUser)){
                System.out.println(RED + "Incorrect Password" + RESET);
                password = ReadInput.readInput("Enter Your Password: ");
            }
            AppSession.currentUser = currentUser;
            AppSession.currentUserId = currentUser.getId();
            AppSession.previousPage = pagenumber;
            navigateToPage(2);
        }
    }
}
