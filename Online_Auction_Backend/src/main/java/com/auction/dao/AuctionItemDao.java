package com.auction.dao;

import com.auction.model.Matcher;
import com.auction.model.RecordClass.AuctionItem;
import com.auction.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class AuctionItemDao {
    private static final Logger logger = LoggerFactory.getLogger(AuctionItemDao.class);

    // Insert Auction Item
    public void insertAuctionItem(AuctionItem item) {

        String sql = "INSERT INTO AuctionItem (item_id, title, category, start_price) VALUES (auction_item_seq.NEXTVAL, ?, ?, ?)";
//for mysql
//        String sql = "INSERT INTO AuctionItem (title, category, start_price) VALUES (?, ?, ?)";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, item.title());
            ps.setString(2, item.category());
            ps.setDouble(3, item.start_price());
            ps.executeUpdate();

            Matcher.match(item);
        } catch (Exception e) {
            logger.error("Error inserting auction item: {}", item.title(), e);
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
                logger.info("Auction item updated: {}", item.title());
            } else {
                logger.warn("No item found with ID: {}", item.item_id());
            }
        } catch (Exception e) {
            logger.error("Error updating auction item: {}", item.title(), e);
        }
    }

    // Display ALL auction items
    public void displayAllAuctionItems() {
        String sql = "SELECT * FROM AuctionItem";

        try {
            Connection con = DBUtil.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            logger.info("\n=== All Auction Items ===");
            while (rs.next()) {
                int id = rs.getInt("item_id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                double price = rs.getDouble("start_price");
                logger.info("Item ID: {}, Title: {}, Category: {}, Starting Price: ₹{}", id, title, category, price);

                System.out.println("Item id: " + rs.getInt("item_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Starting Price: ₹" + rs.getDouble("start_price"));
                System.out.println();
            }
        } catch (Exception e) {
            logger.error("Error fetching all auction items", e);
        }
    }
}