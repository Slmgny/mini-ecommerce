package com.github.daymoon.DAO;

import java.sql.*;
import java.util.ArrayList;

import com.github.daymoon.DBConnection;
import com.github.daymoon.Product;

public class ProductDAO {


    //All
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();


        String sql = "SELECT id, name, price, stock, sellCount, sellerId , description FROM Product";

        try (Connection conn = DBConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int sellCount = rs.getInt("sellCount");
                int sellerId = rs.getInt("sellerId");
                String description = rs.getString("description");


                Product product = new Product(id, name, price, stock, sellCount , sellerId , description);
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


        String sql = "SELECT id, name, price, stock, sellCount, sellerId, description FROM Product WHERE sellerId = ?";

        try (Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, UserId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next() && rs.getInt("sellerId") == UserId) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int sellCount = rs.getInt("sellCount");
                int sellerId = rs.getInt("sellerId");
                String description = rs.getString("description");


                Product product = new Product(id, name, price, sellCount ,  stock , sellerId , description);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(int productId){


        String sql = "SELECT id, name, price, stock, sellCount, sellerId , description FROM Product WHERE id = ?";
        Product product = null;
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, productId);
                ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sellCount = rs.getInt("sellCount");
                int stock = rs.getInt("stock");
                int sellerId = rs.getInt("sellerId");
                String description = rs.getString("description");

                product = new Product(id, name, price, sellCount , stock , sellerId , description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
