// FAIZAN
package com.auction.dao;

import com.auction.model.RecordClass.Bid;
import com.auction.util.DBUtil;

import java.sql.*;

public class BidDao {

    public void placeBid(Bid bid) {


        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO Bid (bid_id, user_id, item_id, amount, bid_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, bid.bid_id());
            preparedStatement.setInt(2, bid.user_id());
            preparedStatement.setInt(3, bid.item_id());
            preparedStatement.setDouble(4, bid.amount());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(bid.bid_time()));

            preparedStatement.executeUpdate();
            System.out.println("Bid placed by User ID: " + bid.user_id());

        } catch (SQLException e) {
            System.out.println("DB Error during bid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Error during DB connection: " + e.getMessage());
        }
    }

    public void displayBid(Bid bid) {
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Bid";
            ResultSet row = st.executeQuery(sql);

            while (row.next()) {
                System.out.println(
                        "Bid ID: " + row.getInt("bid_id") +
                                ", User ID: " + row.getInt("user_id") +
                                ", Item ID: " + row.getInt("item_id") +
                                ", Amount: " + row.getDouble("amount") +
                                ", Time: " + row.getTimestamp("bid_time")
                );
            }

        } catch (SQLException e) {
            System.out.println("DB Error during bid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Error during DB connection: " + e.getMessage());
        }
    }
}
