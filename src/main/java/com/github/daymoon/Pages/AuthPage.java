package com.github.daymoon.Pages;

import static com.github.daymoon.TextColors.*;

import com.github.daymoon.AppSession;
import com.github.daymoon.MarketUser;
import com.github.daymoon.User;

public class AuthPage {
    //Login Sign Up Page
    public void LoginOrSignUpPage(){
        int pagenumber = 1;
        AppSession.currentPage = pagenumber;
        System.out.println(CYAN + "=== LOG IN OR SIGN UP ===" + RESET);
        System.out.println(YELLOW + "You can type '" + MAGENTA + "help" + YELLOW + "' to see the available commands" + RESET);
        String userName = AskName();
        boolean newUser = true;
        User currentUser = null;
        for(User u: userList){
            if(u.getName().equals(userName)){
                newUser = false;
                currentUser = u;
                break;
            }
        }
        if(newUser){ // Sign up
            System.out.println(CYAN + "=== SIGN UP ===" + RESET);
            String password = readInput("Create Your Password: ");
            while(!isPasswordValid(password)){
                password = readInput("Create Your Password: ");
            }
            User user = new MarketUser(userName , password , 1 , 0);
            user.AddToDataBase();
            userList.add(user);
            AppSession.currentUser = user;
            AppSession.currentUserId = user.getId();
            AppSession.previousPage = pagenumber;
            mainPage();
        }else{ // Login
            System.out.println(CYAN + "=== LOG IN ===" + RESET);
            String password = readInput("Enter Your Password: ");
            while(!isPasswordCorrect(password , currentUser)){
                System.out.println(RED + "Incorrect Password" + RESET);
                password = readInput("Enter Your Password: ");
            }
            AppSession.currentUser = currentUser;
            AppSession.currentUserId = currentUser.getId();
            AppSession.previousPage = pagenumber;
            mainPage();
        }
    }
}
