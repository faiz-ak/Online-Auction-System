//FAIZAN
package com.auction.dao;
import com.auction.model.RecordClass.User;
import com.auction.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public void RegisterUser(User user){
        try{
//            Connection established
            Connection con = DBUtil.getConnection();
//            Inserting user
            String sql = "INSERT INTO User (user_id, name, email) VALUES (?, ?, ?)";
//            prepared statement for executing dynamic queries
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, user.user_id());
            preparedStatement.setString(2, user.name());
            preparedStatement.setString(3, user.email());

            preparedStatement.executeUpdate();
            System.out.println("User inserted: " + user.name());
        } catch (SQLException e) {
            System.out.println("DB Error during registering user: " + e.getMessage());
        }
    }
}
