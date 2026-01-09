package com.maruti.dao;

import java.sql.*;
import com.maruti.models.Settings;

public class SettingsDAO {
    Connection con = DBConnection.getConnection();

    // Get settings by user ID
    public Settings getSettingsByUserId(int userId){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM settings WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Settings s = new Settings();
                s.setUserId(rs.getInt("user_id"));
                s.setAppPassword(rs.getString("app_password"));
                s.setDarkMode(rs.getString("dark_mode").charAt(0));
                s.setPushNotification(rs.getString("push_notification").charAt(0));
                s.setAppLock(rs.getString("app_lock").charAt(0));
                return s;
            }
            rs.close();
            ps.close();
        } catch(Exception e){ e.printStackTrace(); }
        return null;
    }

    // Insert new settings for a user
    public void insertSettings(Settings s){
        try{
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO settings(user_id, app_password, dark_mode, push_notification, app_lock) VALUES(?, ?, ?, ?, ?)"
            );
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getAppPassword()); // can be null
            ps.setString(3, String.valueOf(s.getDarkMode()));
            ps.setString(4, String.valueOf(s.getPushNotification()));
            ps.setString(5, String.valueOf(s.getAppLock()));
            ps.executeUpdate();
            ps.close();
        } catch(Exception e){ e.printStackTrace(); }
    }

    // Update dark mode
    public void updateDarkMode(Settings s){
        try{
            Settings existing = getSettingsByUserId(s.getUserId());
            if(existing != null){
                // UPDATE
                PreparedStatement ps = con.prepareStatement("UPDATE settings SET dark_mode=? WHERE user_id=?");
                ps.setString(1, String.valueOf(s.getDarkMode()));
                ps.setInt(2, s.getUserId());
                ps.executeUpdate();
                ps.close();
            } else {
                // INSERT (new row)
                if(s.getAppPassword() == null) s.setAppPassword(null); // default null
                if(s.getPushNotification() == 0) s.setPushNotification('Y');
                if(s.getAppLock() == 0) s.setAppLock('N');
                insertSettings(s);
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    // Update password
    public void updateAppPassword(Settings s){
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE settings SET app_password=? WHERE user_id=?");
            ps.setString(1, s.getAppPassword());
            ps.setInt(2, s.getUserId());
            ps.executeUpdate();
            ps.close();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
