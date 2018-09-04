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
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "UPDATE member "
                + "SET first_name=?, last_name=?, phone_no=?, email=?, address=?, pincode=? "
                + "WHERE id=?";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)){
            myStmt.setString(1, obj.getFirstName());
            myStmt.setString(2, obj.getLastName());
            myStmt.setString(3, obj.getPhoneNo());
            myStmt.setString(4, obj.getEmailId());
            myStmt.setString(5, obj.getAddress());
            myStmt.setInt(6, obj.getPincode());
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing update operation: " + e);
        }
    }

    @Override
    public T delete(Serializable id) {
        return null;
    }

    @Override
    public T get(Serializable id) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "select * from member where id=?";
        Member member = null;
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)){
            myStmt.setInt(1, (Integer) id);

            try (ResultSet myRs = myStmt.executeQuery()) {
                if(myRs.next())
                {
                    String firstName = myRs.getString("first_name");
                    String lastName = myRs.getString("last_name");
                    String phoneNo = myRs.getString("phone_no");
                    String emailId = myRs.getString("email");
                    String password = myRs.getString("password");
                    Member.MemberType memberType = Member.MemberType.valueOf(myRs.getString("type"));
                    String address = myRs.getString("address");
                    int pincode = myRs.getInt("pincode");
                    Member.Status status = Member.Status.valueOf(myRs.getString("status"));
                    member = new Member((Integer) id, firstName, lastName, phoneNo, emailId, password, memberType, address, pincode, status);
                }
                else{
                    throw new SQLException("Could not find member with given userId");
                }
            }

        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "userId " + e);
        }
        return (T) member;
    }

    public int get(String email, String password) {
        /*
        To-do's:
        - check this code
        - password should be hashed
         */

        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "select * from member where email=? and password=?";
        int userId = -1;
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setString(1, email);
            myStmt.setString(2, password);
            try (ResultSet myRs = myStmt.executeQuery()) {
                if (myRs.next()) {
                    userId = myRs.getInt("id");
                } else {
                    throw new SQLException("Could not find member with given email and password");
                }
            }

        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "email and password" + e);
        }
        return userId;
    }
}
