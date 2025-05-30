package com.github.daymoon;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";

    public static Connection connect() {
        try {
            System.out.println("Connected");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to Connect");
            return null;
        }
    }
}
