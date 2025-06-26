package com.auction.dao;

import com.auction.model.RecordClass.AuctionItem;
import com.auction.util.DBUtil;

import java.sql.*;

public class AuctionItemDao {

    // Insert Auction Item
    public void insertAuctionItem(AuctionItem item) {
        String sql = "INSERT INTO AuctionItem (item_id, title, category, start_price) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, item.item_id());
            ps.setString(2, item.title());
            ps.setString(3, item.category());
            ps.setDouble(4, item.start_price());

            ps.executeUpdate();
            System.out.println("Auction item inserted: " + item.title());
        } catch (Exception e) {
            System.out.println("Error inserting auction item: " + e.getMessage());
        }
    }

    // Update Auction Item
    public void updateAuctionItem(AuctionItem item) {
        String sql = "UPDATE AuctionItem SET title = ?, category = ?, start_price = ? WHERE item_id = ?";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.title());
            ps.setString(2, item.category());
            ps.setDouble(3, item.start_price());
            ps.setInt(4, item.item_id()); // fixed index

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Auction item updated: " + item.title());
            } else {
                System.out.println("No item found with ID: " + item.item_id());
            }
        } catch (Exception e) {
            System.out.println("Error updating auction item: " + e.getMessage());
        }
    }

    // Display ALL auction items
    public void displayAllAuctionItems() {
        String sql = "SELECT * FROM AuctionItem";

        try {
            Connection con = DBUtil.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n=== All Auction Items ===");
            while (rs.next()) {
                System.out.println("Item ID: " + rs.getInt("item_id"));
                System.out.println("Title: " + rs.getString("title")); // changed from "name" to "title"
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Starting Price: $" + rs.getDouble("start_price"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching all auction items: " + e.getMessage());
        }
    }
}
