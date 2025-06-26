package com.auction.dao;

import com.auction.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionItem {
    private int id;
    private String name;
    private String description;
    private double startingPrice;
    private int sellerId;

    // Constructor
    public AuctionItem(int id, String name, String description, double startingPrice, int sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.sellerId = sellerId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public int getSellerId() {
        return sellerId;
    }

    // Method to insert a new auction item into the database
    public void createAuctionItem() {
        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO auction_items (item_id, name, description, starting_price, seller_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.description);
            preparedStatement.setDouble(4, this.startingPrice);
            preparedStatement.setInt(5, this.sellerId);

            preparedStatement.executeUpdate();
            System.out.println("Auction item created: " + this.name);
        } catch (SQLException e) {
            System.out.println("DB Error during auction item creation: " + e.getMessage());
        }
    }

    // Method to update an existing auction item in the database
    public void updateAuctionItem(String newName, String newDescription, double newStartingPrice) {
        try {
            Connection con = DBUtil.getConnection();
            String sql = "UPDATE auction_items SET name = ?, description = ?, starting_price = ? WHERE item_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setDouble(3, newStartingPrice);
            preparedStatement.setInt(4, this.id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                this.name = newName;
                this.description = newDescription;
                this.startingPrice = newStartingPrice;
                System.out.println("Auction item updated: " + this.name);
            } else {
                System.out.println("No auction item found with ID: " + this.id);
            }
        } catch (SQLException e) {
            System.out.println("DB Error during auction item update: " + e.getMessage());
        }
    }

    // Method to get the highest bid for this auction item
    public double getHighestBid() {
        double highestBid = this.startingPrice;
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT MAX(amount) AS max_bid FROM bids WHERE item_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getDouble("max_bid") > 0) {
                highestBid = resultSet.getDouble("max_bid");
            }
        } catch (SQLException e) {
            System.out.println("DB Error while fetching highest bid: " + e.getMessage());
        }
        return highestBid;
    }

    // Method to display auction item details and associated bids
    public void displayAuctionDetails() {
        try {
            Connection con = DBUtil.getConnection();
            // Fetch auction item details
            String itemSql = "SELECT name, description, starting_price, seller_id FROM auction_items WHERE item_id = ?";
            PreparedStatement itemStatement = con.prepareStatement(itemSql);
            itemStatement.setInt(1, this.id);
            ResultSet itemResult = itemStatement.executeQuery();

            if (itemResult.next()) {
                System.out.println("\n=== Auction Item Details ===");
                System.out.println("Item ID: " + this.id);
                System.out.println("Name: " + itemResult.getString("name"));
                System.out.println("Description: " + itemResult.getString("description"));
                System.out.println("Starting Price: $" + itemResult.getDouble("starting_price"));
                System.out.println("Seller ID: " + itemResult.getInt("seller_id"));
                System.out.println("Highest Bid: $" + getHighestBid());
            } else {
                System.out.println("No auction item found with ID: " + this.id);
                return;
            }

            // Fetch all bids for this item
            String bidSql = "SELECT bid_id, user_id, amount, bid_time FROM bids WHERE item_id = ? ORDER BY amount DESC";
            PreparedStatement bidStatement = con.prepareStatement(bidSql);
            bidStatement.setInt(1, this.id);
            ResultSet bidResult = bidStatement.executeQuery();

            System.out.println("\n=== Bids for Item ID " + this.id + " ===");
            boolean hasBids = false;
            while (bidResult.next()) {
                hasBids = true;
                System.out.println("Bid ID: " + bidResult.getInt("bid_id") +
                        ", User ID: " + bidResult.getInt("user_id") +
                        ", Amount: $" + bidResult.getDouble("amount") +
                        ", Time: " + bidResult.getTimestamp("bid_time"));
            }
            if (!hasBids) {
                System.out.println("No bids placed for this item.");
            }
        } catch (SQLException e) {
            System.out.println("DB Error while fetching auction details: " + e.getMessage());
        }
    }
}