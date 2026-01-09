package com.maruti.dao;

import java.sql.*;
import com.maruti.models.Product;
import java.util.*;

public class ProductDAO {
    Connection con = DBConnection.getConnection();

    // Get all products for a user
    public List<Product> getAllProducts(int userId){
        List<Product> list = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE user_id=? ORDER BY product_id DESC");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setName(rs.getString("name"));
                p.setUnit(rs.getString("unit"));
                p.setQty(rs.getInt("qty"));
                p.setMinQty(rs.getInt("min_qty"));
                p.setType(rs.getString("type"));
                p.setLastUpdated(rs.getTimestamp("last_updated"));
                list.add(p);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    // Get product by name & unit
    public Product getProductByNameUnit(int userId, String name, String unit){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE user_id=? AND name=? AND unit=?");
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, unit);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setName(rs.getString("name"));
                p.setUnit(rs.getString("unit"));
                p.setQty(rs.getInt("qty"));
                p.setMinQty(rs.getInt("min_qty"));
                p.setType(rs.getString("type"));
                p.setLastUpdated(rs.getTimestamp("last_updated"));
                return p;
            }
        } catch(Exception e){ e.printStackTrace(); }
        return null;
    }

    // Update existing product by original name & unit
    public void updateProductByOriginalNameAndUnit(int userId, String originalName, String unit, Product p){
        try{
            PreparedStatement ps = con.prepareStatement(
                "UPDATE products SET name=?, qty=?, min_qty=?, type=?, last_updated=CURRENT_TIMESTAMP WHERE user_id=? AND name=? AND unit=?"
            );
            ps.setString(1, p.getName());
            ps.setInt(2, p.getQty());
            ps.setInt(3, p.getMinQty());
            ps.setString(4, p.getType());
            ps.setInt(5, userId);
            ps.setString(6, originalName);
            ps.setString(7, unit);
            int updated = ps.executeUpdate();

            // If no row was updated → insert new product
            if(updated == 0){
                insertProduct(p);
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    // Insert new product
    public void insertProduct(Product p){
        try{
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO products(user_id, name, qty, unit, min_qty, type, last_updated) VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP)"
            );
            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getQty());
            ps.setString(4, p.getUnit());
            ps.setInt(5, p.getMinQty());
            ps.setString(6, p.getType());
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    // Update existing product by product_id (needed for ProductServlet)
    public void updateProduct(Product p){
        try{
            PreparedStatement ps = con.prepareStatement(
                "UPDATE products SET name=?, qty=?, unit=?, min_qty=?, type=?, last_updated=CURRENT_TIMESTAMP WHERE product_id=?"
            );
            ps.setString(1, p.getName());
            ps.setInt(2, p.getQty());
            ps.setString(3, p.getUnit());
            ps.setInt(4, p.getMinQty());
            ps.setString(5, p.getType());
            ps.setInt(6, p.getProductId());
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    // Delete product by id
    public void deleteProduct(int productId){
        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE product_id=?");
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
