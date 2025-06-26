//FAIZAN
package com.auction.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static final String url = "jdbc:mysql://localhost:3306/auction";
    private static final String username = "root";
    private static final String password = "Admin@123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver: " + e.getMessage());
        }
    }
       public static Connection getConnection() throws Exception{
            return DriverManager.getConnection(url, username, password);
        }
}
