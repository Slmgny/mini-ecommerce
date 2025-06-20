package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.RED;
import static com.github.daymoon.Utils.TextColors.RESET;

import com.github.daymoon.Models.User;
import com.github.daymoon.Utils.AppSession;

public class Validate{
    
    public static Boolean isPasswordValid(String password){
        if(password.length() < 8){
            System.out.println(RED + "Password must be at least 8 characters" + RESET);
            return false;
        }

        if(!password.matches(".*[A-Z].*")){
            System.out.println(RED + "Password must contain at least 1 capital letter" + RESET);
            return false;
        }

        if(!password.matches(".*[a-z].*")){
            System.out.println(RED + "Password must contain at least 1 lowercase letter" + RESET);
            return false;
        }

        if(!password.matches(".*\\d.*")){
            System.out.println(RED + "Password must contain at least 1 number" + RESET);
            return false;
        }

        return true;
    }

    public static Boolean isPasswordCorrect(String password , User u){
        return u.getPassword().equals(password);
    }

    public static Boolean canBuy(double totalPrice){
        if(AppSession.currentUser.getMoney() < totalPrice){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
            return false;
        }
        return true;
    }

}
