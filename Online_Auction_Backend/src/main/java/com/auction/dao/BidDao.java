// FAIZAN
package com.auction.dao;

import com.auction.model.Matcher;
import com.auction.model.RecordClass.Bid;
import com.auction.model.RecordClass.PlacedTransaction;
import com.auction.util.DBUtil;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BidDao {

    private static final Logger logger = LoggerFactory.getLogger(BidDao.class);

    public void placeBid(Bid bid) {
//        String checkAuctionSql = "SELECT bid_time FROM AuctionItem WHERE item_id = ?";
//        try{
//            Connection con = DBUtil.getConnection();
//            PreparedStatement psCheck = con.prepareStatement(checkAuctionSql)
//            psCheck.setInt(1, bid.item_id());
//            ResultSet rs = psCheck.executeQuery();
//            if (rs.next()) {
//                Timestamp endTimeTs = rs.getTimestamp("bid_time");
//                LocalDateTime auctionEndTime = endTimeTs.toLocalDateTime();
//                LocalDateTime now = LocalDateTime.now();
//                if (now.isAfter(auctionEndTime)) {
//                    logger.warn("Auction for Item ID {} expired at {}. Bid not accepted.",
//                            bid.item_id(), auctionEndTime);
//                    return;
//                }
//            } else {
//                logger.warn("Auction item with ID {} not found. Bid cannot be placed.", bid.item_id());
//                return;
//            }
//        } catch (SQLException e) {
//            logger.error("Error checking auction expiration: {}", e.getMessage(), e);
//            return;
//        } catch (Exception e) {
//            logger.error("General error checking auction expiration: {}", e.getMessage(), e);
//            return;
//        }

        try {
            Connection con = DBUtil.getConnection();
//            String sql = "INSERT INTO Bid (bid_id, user_id, item_id, amount, bid_time) VALUES (bid_seq.NEXTVAL, ?, ?, ?, ?)";
//for mysql
            String sql = "INSERT INTO Bid (user_id, item_id, amount, bid_time) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, bid.user_id());
            preparedStatement.setInt(2, bid.item_id());
            preparedStatement.setDouble(3, bid.amount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(bid.bid_time()));
            preparedStatement.executeUpdate();
            Matcher.match(bid);
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

    public void placeTransaction(PlacedTransaction transaction) {
        String sql = "INSERT INTO PlacedTransaction (item_id, buyer_id, amount, transaction_time) VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, transaction.item_id());
            preparedStatement.setInt(2, transaction.buyer_id());
            preparedStatement.setDouble(3, transaction.amount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(transaction.transaction_time()));

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                int generatedId = keys.getInt(1);
                logger.info("Transaction placed successfully with Transaction ID: {}", generatedId);
            }

        } catch (SQLException e) {
            logger.error("DB Error during transaction: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("General Error during DB connection: {}", e.getMessage(), e);
        }
    }
    public void finalizeWinningTransactions() {
        String sql = "SELECT b.item_id, b.user_id, b.amount " +
                "FROM Bid b " +
                "INNER JOIN ( " +
                "   SELECT item_id, MAX(amount) AS max_amount " +
                "   FROM Bid GROUP BY item_id " +
                ") max_bids ON b.item_id = max_bids.item_id AND b.amount = max_bids.max_amount";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            TransactionDao transactionDao = new TransactionDao();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int buyerId = rs.getInt("user_id");
                double amount = rs.getDouble("amount");

                PlacedTransaction transaction = new PlacedTransaction(
                        0,
                        itemId,
                        buyerId,
                        amount,
                        new Timestamp(System.currentTimeMillis()).toLocalDateTime()
                );

                transactionDao.placeTransaction(transaction);

                logger.info("Winning transaction finalized - Item ID: {}, Buyer ID: {}, Amount: {}",
                        itemId, buyerId, amount);
            }

        } catch (SQLException e) {
            logger.error("Error finalizing winning transactions: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("General error finalizing winning transactions: {}", e.getMessage(), e);
        }
    }

}