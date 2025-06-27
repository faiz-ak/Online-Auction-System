// FAIZAN
package com.auction.dao;

import com.auction.model.RecordClass.Bid;
import com.auction.util.DBUtil;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BidDao {

    private static final Logger logger = LoggerFactory.getLogger(BidDao.class);

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

            logger.info("Bid placed by User ID: {} for Item ID: {} with Amount: {} at {}",
                    bid.user_id(), bid.item_id(), bid.amount(), bid.bid_time());

        } catch (SQLException e) {

            logger.error("DB error during bid {}", e.getMessage(), e);
        } catch (Exception e) {

            logger.error("General Error during DB connection :{} ", e.getMessage(),e);
        }
    }

    public void displayBid(Bid bid) {
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Bid";
            ResultSet row = st.executeQuery(sql);

            logger.info("Fetching all bids from database...");

            while (row.next()) {
                int bidId = row.getInt("bid_id");
                int userId = row.getInt("user_id");
                int itemId = row.getInt("item_id");
                double amount = row.getDouble("amount");
                Timestamp time = row.getTimestamp("bid_time");


                logger.info("Bid ID: {}, User ID: {}, Item ID: {}, Amount: {}, Time: {}",
                        bidId, userId, itemId, amount, time);
            }

        } catch (SQLException e) {
            logger.error("DB error during bid display: {}", e.getMessage(),e);
        } catch (Exception e) {
            logger.error("General error during DB connection: {}", e.getMessage(),e);
        }
    }
}
