package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Utils.ReadInput;

public class GetInfo {

    public static String askName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = ReadInput.readInput("Name: ");
            if(name.length() <= 4){
                System.out.println(RED + "User Name must be at least 4 characters" + RESET);
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println(RED + "User Name Must contain at least 1 letter" + RESET);
                continue;
        }
            isCorrect = true;
            return name;
        }
        return null;

    }
    
    public static double askPrice(){

        Boolean isCorrect = false;
        while(!isCorrect){
            double price = ReadInput.readDoubleInput("Price: ");
            if(price <= 0){
                System.out.println(RED + "Price can't be less than or equal to 0" + RESET);
                continue;
        }
        isCorrect = true;
        return price;
        }
        return -1;
    }

    public static String askProductName(){

        Boolean isCorrect = false;
        while(!isCorrect){
            String name = ReadInput.readInput("Enter Product Name: ");
            if(name.length() < 4){
                System.out.println(RED + "Product Name must be at least 4 characters" + RESET);
                continue;
        }
            if(!name.matches(".*[a-zA-Z].*")){
                System.out.println(RED +"Product Name Must contain at least 1 letter" + RESET);
                continue;
        }
        isCorrect = true;
        return name;
        }
        return null;

    }

    public static int askStock(){

        Boolean isCorrect = false;
        while(!isCorrect){
            int stock = ReadInput.readIntInput("Stock: ");
            if(stock <= 0){
                System.out.println(RED + "Stock can't be less than 0" + RESET);
                continue;
        }
        isCorrect = true;
        return stock;
        }
        return -1;
    }

}
