// FAIZAN
package com.auction.dao;

import com.auction.util.DBUtil;
import com.auction.model.RecordClass.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDao {

    public void placeTransaction(Transaction transaction) {

        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO transactions (transaction_id, item_id, buyer_id, amount, transaction_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, transaction.id());
            preparedStatement.setInt(2, transaction.itemId());
            preparedStatement.setInt(3, transaction.buyerId());
            preparedStatement.setDouble(4, transaction.amount());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.transactionTime()));

            preparedStatement.executeUpdate();
            System.out.println("Transaction placed for Item ID: " + transaction.itemId());

        } catch (SQLException e) {
            System.out.println("DB Error during transaction: " + e.getMessage());
        }
    }
}
