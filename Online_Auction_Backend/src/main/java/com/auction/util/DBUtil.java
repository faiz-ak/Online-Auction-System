//FAIZAN
package com.auction.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
//FOR ORACLE DB CONNECTION
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "System";
    private static final String password = "Admin@123";

////FOR SQL DB CONNECTION
//    private static final String url = "jdbc:mysql://localhost:3306/auction";
//    private static final String username = "root";
//    private static final String password = "Admin@123";

    static {
        try {
            //FOR ORACLE DB DRIVER
            Class.forName("oracle.jdbc.OracleDriver");
            //FOR SQL DB DRIVER
//            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver: " + e.getMessage());
        }
    }
       public static Connection getConnection() throws Exception{
            return DriverManager.getConnection(url, username, password);
        }
}
