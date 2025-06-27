//FAIZAN
package com.auction.dao;
import com.auction.model.RecordClass.Users;
import com.auction.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void RegisterUser(Users user){
        try{
//            Connection established
            Connection con = DBUtil.getConnection();
//            Inserting user
            String sql = "INSERT INTO Users (user_id, name, email) VALUES (?,?,?)";
//            prepared statement for executing dynamic queries
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user.user_id());
            preparedStatement.setString(2, user.name());
            preparedStatement.setString(3, user.email());

            preparedStatement.executeUpdate();

            logger.info("User registered:{}",user.name());
        } catch (SQLException e) {

            logger.error("DB error while registering user '{}': {}", user.name(), e.getMessage(), e);
        } catch (Exception e) {

            logger.error("General Error during DB connection:{}", e.getMessage(), e);
        }
    }
}
