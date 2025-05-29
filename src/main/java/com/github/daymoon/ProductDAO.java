package com.github.daymoon;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {


    //All
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";
        String sql = "SELECT id, name, price, stock, sellCount, sellerId FROM Products";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                int sellCount = rs.getInt("sellCount");
                int sellerId = rs.getInt("sellerId");

                Product product = new Product(id, name, price, stock, sellCount , sellerId);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    //By User Id
    public ArrayList<Product> getAllProductsBySellerId(int UserId) {
        ArrayList<Product> products = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Java\\mini-ecommerce\\DataBase\\mini-ecommerce-database.db";
        String sql = "SELECT id, name, price, stock, sellCount, sellerId FROM Products";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next() && rs.getInt("sellerId") == UserId) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                int sellCount = rs.getInt("sellCount");
                int sellerId = rs.getInt("sellerId");

                Product product = new Product(id, name, price, stock, sellCount , sellerId);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
