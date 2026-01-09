package com.maruti.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String url =
             "jdbc:postgresql://db.iimiqbyffhbysdsynpro.supabase.co:5432/postgres?sslmode=require";

            con = DriverManager.getConnection(
                    url,
                    "postgres",
                    "Stock@2026#DB"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

