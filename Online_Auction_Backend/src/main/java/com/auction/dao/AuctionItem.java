package com.auction.dao;

import com.auction.model.RecordClass.AuctionItem;
import com.auction.util.DBUtil;

import java.sql.*;

public class AuctionItemDao {

    // Insert Auction Item
    public void insertAuctionItem(AuctionItem item) {
        String sql = "INSERT INTO auction_items (item_id, name, category, starting_price, end_time, seller_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, item.id());
            ps.setString(2, item.name());
            ps.setString(3, item.category());
            ps.setDouble(4, item.startingPrice());
            ps.setTimestamp(5, Timestamp.valueOf(item.endTime()));
            ps.setInt(6, item.sellerId());

            ps.executeUpdate();
            System.out.println("Auction item inserted: " + item.name());
        } catch (SQLException e) {
            System.out.println("Error inserting auction item: " + e.getMessage());
        }
    }

    // Update Auction Item
    public void updateAuctionItem(AuctionItem item) {
        String sql = "UPDATE auction_items SET name = ?, category = ?, starting_price = ?, WHERE item_id = ?";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.name());
            ps.setString(2, item.category());
            ps.setDouble(3, item.startingPrice());
            ps.setInt(5, item.id());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Auction item updated: " + item.name());
            } else {
                System.out.println("No item found with ID: " + item.id());
            }
        } catch (SQLException e) {
            System.out.println("Error updating auction item: " + e.getMessage());
        }
    }

    // Display ALL auction items (you asked for this)
    public void displayAllAuctionItems() {
        String sql = "SELECT * FROM auction_items";

        try {
            Connection con = DBUtil.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n=== All Auction Items ===");
            while (rs.next()) {
                System.out.println("Item ID: " + rs.getInt("item_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Starting Price: $" + rs.getDouble("starting_price"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all auction items: " + e.getMessage());
        }
    }
}