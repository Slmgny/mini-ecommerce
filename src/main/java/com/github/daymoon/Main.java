package com.github.daymoon;

import static com.github.daymoon.Navigate.navigateToPage;
import static com.github.daymoon.Utils.TextColors.*;


public class Main {

    
    
    public static void main(String[] args) {
        while(true){
            System.out.println(BLUE + "Welcome to Mini E-Commerce App" + RESET);
            ArrayLists.init();
            navigateToPage(1);
        }
        
    }


}
