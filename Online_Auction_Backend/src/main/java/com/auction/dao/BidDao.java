// FAIZAN
package com.auction.dao;

import com.auction.model.RecordClass.Bid;
import com.auction.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BidDao {

    public void placeBid(Bid bid) {


        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO bids (bid_id, user_id, item_id, amount, bid_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, bid.id());
            preparedStatement.setInt(2, bid.userId());
            preparedStatement.setInt(3, bid.itemId());
            preparedStatement.setDouble(4, bid.amount());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(bid.bidTime()));

            preparedStatement.executeUpdate();
            System.out.println("Bid placed by User ID: " + bid.userId());

        } catch (SQLException e) {
            System.out.println("DB Error during bid: " + e.getMessage());
        }
    }
}
