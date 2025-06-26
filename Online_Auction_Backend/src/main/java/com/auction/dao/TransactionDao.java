// FAIZAN
package com.auction.dao;

import com.auction.util.DBUtil;
import com.auction.model.RecordClass.PlacedTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDao {

    public void placeTransaction(PlacedTransaction transaction) {

        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO PlacedTransaction (item_id, buyer_id, amount, transaction_time) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.item_id());
            preparedStatement.setInt(2, transaction.buyer_id());
            preparedStatement.setDouble(3, transaction.amount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(transaction.transaction_time()));

            preparedStatement.executeUpdate();
            System.out.println("\nTransaction placed for Item ID: " + transaction.item_id());

        } catch (SQLException e) {
            System.out.println("DB Error during transaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Error during DB connection: " + e.getMessage());
        }
    }
}
