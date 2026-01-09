package com.maruti.dao;

import java.sql.*;
import com.maruti.models.User;

public class UserDAO {

    Connection con = DBConnection.getConnection();

    // 🔹 Check if mobile exists (USED IN SIGNUP)
    public boolean checkMobileExists(String mobile) {
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT 1 FROM users WHERE mobile=?");
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Check if email exists (USED IN SIGNUP)
    public boolean checkEmailExists(String email) {
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT 1 FROM users WHERE username=?"); // username = email
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Add new user (SIGNUP)
    public void addUser(User user) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(first_name,last_name,username,mobile,password) VALUES(?,?,?,?,?)"
            );
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername()); // EMAIL
            ps.setString(4, user.getMobile());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Get user by mobile (OLD FLOW – KEPT)
    public User getUserByMobile(String mobile) {
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users WHERE mobile=?");
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setUsername(rs.getString("username"));
                u.setMobile(rs.getString("mobile"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Get user by email (NEW – FOR EMAIL LOGIN)
    public User getUserByEmail(String email) {
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users WHERE username=?"); // username = email
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setUsername(rs.getString("username"));
                u.setMobile(rs.getString("mobile"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Update password (UNCHANGED)
    public void updatePassword(User user) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET password=? WHERE user_id=?"
            );
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Save OTP (OPTIONAL – KEPT AS IS)
    public void saveOTP(String mobile, String otp) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "MERGE INTO otp_table o USING dual ON (o.mobile=?) " +
                "WHEN MATCHED THEN UPDATE SET o.otp=? " +
                "WHEN NOT MATCHED THEN INSERT (mobile, otp) VALUES (?, ?)"
            );
            ps.setString(1, mobile);
            ps.setString(2, otp);
            ps.setString(3, mobile);
            ps.setString(4, otp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Verify OTP (OPTIONAL – KEPT)
    public boolean verifyOTP(String mobile, String otp) {
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT 1 FROM otp_table WHERE mobile=? AND otp=?");
            ps.setString(1, mobile);
            ps.setString(2, otp);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
