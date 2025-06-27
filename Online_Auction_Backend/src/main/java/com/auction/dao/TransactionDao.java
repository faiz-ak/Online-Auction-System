// FAIZAN
package com.auction.dao;

import com.auction.util.DBUtil;
import com.auction.model.RecordClass.PlacedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDao {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

    public void placeTransaction(PlacedTransaction transaction) {

        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO PlacedTransaction (transaction_id, item_id, buyer_id, amount, transaction_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.transaction_id());
            preparedStatement.setInt(2, transaction.item_id());
            preparedStatement.setInt(3, transaction.buyer_id());
            preparedStatement.setDouble(4, transaction.amount());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.transaction_time()));

            preparedStatement.executeUpdate();

            logger.info("Transaction placed for Item ID: {}",transaction.item_id());

        } catch (SQLException e) {

            logger.error("DB Error during transaction: {}",e.getMessage(), e);
        } catch (Exception e) {

            logger.error("General Error during DB connection: {}", e.getMessage(), e);

        }
    }
}