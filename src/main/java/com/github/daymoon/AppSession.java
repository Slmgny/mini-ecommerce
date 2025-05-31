package com.github.daymoon;

public class AppSession {
    
    public static int previousPage = 1;
    public static User currentUser;
    public static int currentUserId = -1;
    static{
        if(currentUser != null){
            currentUserId = currentUser.getId();
        }
    }
    
    

}
