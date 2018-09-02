package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Member;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberDAO<T extends Member> implements DAO<T> {

    @Override
    public void create(T obj) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "insert into member "
                + "(first_name, last_name, phone_no, email, password, type," +
                "address, pincode)"
                + "values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            myStmt.setString(1, obj.getFirstName());
            myStmt.setString(2, obj.getLastName());
            myStmt.setString(3, obj.getPhoneNo());
            myStmt.setString(4, obj.getEmailId());
            myStmt.setString(5, obj.getPassword());
            myStmt.setString(6, String.valueOf(obj.getType()));
            myStmt.setString(7, obj.getAddress());
            myStmt.setInt(8, obj.getPincode());
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("User creation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing insert operation: " + e);
        }
    }

    @Override
    public void update(T obj) {

    }

    @Override
    public T delete(Serializable id) {
        return null;
    }

    @Override
    public T get(Serializable id) {
        return null;
    }

    public int get(String email, String password) {
        /*
        To-do's:
        - check this code
        - password should be hashed
         */

        Connection myConn = MyApplicationContext.getJdbcConnection();
        int userId = -1;
        try{
            PreparedStatement myStmt = null;
            ResultSet myRs = null;

            String sql = "select * from member where email=? and password=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, email);
            myStmt.setString(2, password);
            myRs = myStmt.executeQuery();
            if(myRs.next())
            {
                 userId = myRs.getInt("id");
            }
            else{
                throw new SQLException("Could not find member with given email and password");
            }

        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "email and password" + e);
        }
        return userId;
    }
}
